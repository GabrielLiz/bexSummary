package com.bex.btca.batch;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.HsqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.bex.btca.Window;
import com.bex.btca.listener.JobListener;
import com.bex.btca.listener.StepListener;
import com.bex.btca.model.EstadisticasRFQ;
import com.bex.btca.model.Totales;
import com.bex.btca.model.Trade;
import com.bex.btca.procesado.BTCAprocessor;
import com.bex.btca.procesado.RFQprocessor;
import com.bex.btca.steps.TaskletStep;
import com.bex.btca.writer.TradesWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfig  {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	MultiResourceItemReader<Trade> readers;

	public BatchConfig() {
		super();

	}

	///////////////// READERS //////////////////
	@Bean
	public FlatFileItemReader<Trade> readerCarga() {
		FlatFileItemReader<Trade> reader = new FlatFileItemReader<Trade>();
		reader.setLinesToSkip(1);
		// reader.setResource(new FileSystemResource("C:/CSV/total.csv"));

		reader.setLineMapper(new DefaultLineMapper<Trade>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setStrict(false);
						setDelimiter(";");
						setNames(new String[] { "IDdetransacción", "Version", "Acción", "Estatus", "FeedProducer",
								"Repetir", "Razón", "Descripcióndelvalor", "ISIN", "Enviado", "Received",
								"Tipodeoperación", "TradeSubtype", "TradingMode", "TradingPlatform", "TradingSystem",
								"Capacity", "Cantidad", "Tipodecantidad", "Precio", "Divisadelprecio", "PriceType",
								"Centro", "Fechadetransacción", "AssetClass", "OrderType", "OrderAccountType", "Costs",
								"Rebates", "LastCapacity", "BestBid", "BestOffer", "IDdeejecutor", "Tipodeejecutor",
								"IDdecomprador", "Tipodecomprador", "IDdevendedor", "Tipodevendedor", "Últmensaje" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Trade>() {
					{
						setTargetType(Trade.class);
					}
				});
			}
		});

		return reader;
	}

	@Bean
	JdbcPagingItemReader<Totales> readerRfq(DataSource dataSource) {
		PagingQueryProvider provider = createQueryProvider("SELECT *", "FROM btca", "WHERE version !=''");
		JdbcPagingItemReader<Totales> databaseReader = new JdbcPagingItemReaderBuilder<Totales>().name("tratamientoRFQ")
				.dataSource(dataSource).rowMapper(new BeanPropertyRowMapper<>(Totales.class)).queryProvider(provider)
				.build();

		return databaseReader;
	}

	@Bean
	JdbcPagingItemReader<Totales> readerTrade(DataSource dataSource) {
		
		PagingQueryProvider provider = createQueryProvider("SELECT *", "FROM btca",
				"WHERE version ='' AND id_trans !=''");
		JdbcPagingItemReader<Totales> databaseReader = new JdbcPagingItemReaderBuilder<Totales>().name("readerTrade")
				.dataSource(dataSource).rowMapper(new BeanPropertyRowMapper<>(Totales.class)).queryProvider(provider)
				.build();

		return databaseReader;
	}

///////////////////////////////////////Processor/////////////////////////////////////////////

	@Bean
	public BTCAprocessor processor() {

		return new BTCAprocessor();
	}

	// fuera de uso
	@Bean
	public RFQprocessor rfqProcessor() {

		return new RFQprocessor();
	}

///////////////////////////////////WRITERS/////////////////////////////////////
	@Bean
	public FlatFileItemWriter<EstadisticasRFQ> writeTrade() {

		FlatFileItemWriter<EstadisticasRFQ> writer = new FlatFileItemWriter<EstadisticasRFQ>();
		writer.setResource(new FileSystemResource("/CSV/Trades.csv"));
		BeanWrapperFieldExtractor<EstadisticasRFQ> fieldExtractor = new BeanWrapperFieldExtractor<EstadisticasRFQ>();

		writer.setLineAggregator(new DelimitedLineAggregator<EstadisticasRFQ>() {
			{
				setDelimiter(";");
				setFieldExtractor(new BeanWrapperFieldExtractor<EstadisticasRFQ>() {
					{
						setNames(new String[] { "BBVAEQC" });
					}
				});
			}
		});
		return writer;
	}

	@Bean
	public JdbcBatchItemWriter<Totales> writeCarga(DataSource datasource) {
		return new JdbcBatchItemWriterBuilder<Totales>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Totales>())
				.sql("INSERT INTO btca (id_trans, version, status, isin, sent, trade_type, fecha_operativa, assset_class) VALUES (:id_trans, :version, :status, :isin, :sent, :trade_type, :fecha_operativa, :assset_class)")
				.dataSource(datasource).build();
	}

	@Bean
	public JdbcBatchItemWriter<EstadisticasRFQ> writerRfq(DataSource datasource) {
		return new JdbcBatchItemWriterBuilder<EstadisticasRFQ>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<EstadisticasRFQ>())
				.sql("INSERT INTO rfq (version) VALUES (:version)").dataSource(datasource).build();
	}

//////////////////////////////////////STEPS////////////////////////////////////////////////////////////////////

	@Bean
	public Step step1(JdbcBatchItemWriter<Totales> writer, DataSource datasource, StepListener lis) {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(6);
		taskExecutor.setMaxPoolSize(6);
		taskExecutor.afterPropertiesSet();
		return stepBuilderFactory.get("carga").<Trade, Totales>chunk(1000).reader(this.readerMultiResource())
				.processor(this.processor()).writer(this.writeCarga(datasource)).listener(lis)
				// .taskExecutor(taskExecutor) velocidad de multi hilo
				.build();
	}

	@Bean
	public Step step2(DataSource data, StepListener lis) {

		return stepBuilderFactory.get("RFQs")
				// .tasklet(new TaskletStep(data))
				.<Totales, EstadisticasRFQ>chunk(1000).reader(this.readerRfq(data)).processor(new RFQprocessor())
				.writer(this.writerRfq(data)).listener(lis).build();
	}

	@Bean
	public Step step3(DataSource data, StepListener lis) {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(6);
		taskExecutor.setMaxPoolSize(6);
		taskExecutor.afterPropertiesSet();
		return stepBuilderFactory.get("PostTrade").<Totales, Totales>chunk(1000).reader(this.readerTrade(data))
				.writer(new TradesWriter(data))
				.listener(lis)
				//.taskExecutor(taskExecutor) 
				.build();
	}

	@Bean
	public Step step4(DataSource data, StepListener lis) {
		return stepBuilderFactory.get("escribiendo").tasklet(new TaskletStep(data)).listener(lis).build();
	}
	
	//JOB
	@Bean
	public Job TradesJob(JobListener listener, Step step1, DataSource data, StepListener lis) {
		// ejecutar steps en paralelo
		Flow rfqStep = new FlowBuilder<Flow>("rfqStep").start(step2(data, lis)).build();
		Flow tradeStep = new FlowBuilder<Flow>("tradeStep").start(step3(data, lis)).build();
		Flow carga = new FlowBuilder<Flow>("carga").start(step1).build();
		Job job=null;
		if(!Window.trade||!Window.rfqs) {
			if(Window.trade){
			job = jobBuilderFactory
					.get("TradesJob")
					.incrementer(new RunIdIncrementer())
					.listener(listener)
					.start(carga)
					.next(tradeStep)
					.next(this.step4(data, lis))
					.end().build();
			return job;
		}
		if(Window.rfqs) {
			 job = jobBuilderFactory
					.get("TradesJob")
					.incrementer(new RunIdIncrementer())
					.listener(listener)
					.start(carga)
					.next(rfqStep)
					.next(this.step4(data, lis))
					.end().build();
			 return job;
		}
	}else {
			job = jobBuilderFactory
					.get("TradesJob")
					.incrementer(new RunIdIncrementer())
					.listener(listener)
					.start(carga)
					.next(tradeStep)
					.next(rfqStep)
					.next(this.step4(data, lis))
					.end().build();
		}

		return job;
	}

	///////////////////////////// UTILS////////////////////////////////////
	// paginacion y ordanamiento del queryProvider
	private PagingQueryProvider createQueryProvider(String select, String From, String Where) {
		HsqlPagingQueryProvider queryProvider = new HsqlPagingQueryProvider();
		queryProvider.setSelectClause(select);
		queryProvider.setFromClause(From);
		queryProvider.setWhereClause(Where);
		queryProvider.setSortKeys(sortingByID());

		return queryProvider;
	}

	private Map<String, Order> sortingByID() {
		Map<String, Order> sortConfiguration = new HashMap<>();
		sortConfiguration.put("id", Order.ASCENDING);
		return sortConfiguration;
	}

	// deprecated
	@Bean(destroyMethod = "")
	public ItemReader<Totales> readerEstadisticas(DataSource datasource) {
		JdbcCursorItemReader<Totales> reader = new JdbcCursorItemReader<Totales>();
		reader.setDataSource(datasource);
		reader.setSql("SELECT COUNT (version) FROM btca");
		reader.setRowMapper(new BeanPropertyRowMapper<Totales>(Totales.class));
		return reader;
	}
 
	// lectura de multiples ficheros
	@Bean
	public MultiResourceItemReader<Trade> readerMultiResource() {
		ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
		Resource[] resources2 = null;
		try {
			resources2 = patternResolver.getResources("file:C:/CSV/*total.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		readers = new MultiResourceItemReader<>();
		readers.setResources(resources2);
		readers.setDelegate(readerCarga());
		return readers;

	}
}
