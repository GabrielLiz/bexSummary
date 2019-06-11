package com.bex.btca;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Scrollable;

import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Window extends JFrame implements ActionListener, ItemListener {
	
	public static JEditorPane text;
	public static JButton ejecutar;
	public static boolean trade=true;
	public static boolean rfqs=true;
	public static boolean subir=true;
	JCheckBox CheckTrade;
	JCheckBox CheckRfq;
	JCheckBox CheckonlineUP;


	public static void main(String[] args) {
			Window a =new Window();
			a.start();

	}
	
	public Window() throws HeadlessException {
		super();
		 
	}
	public void start() {
	  	ejecutar = new JButton("Analizar Ficheros");
        text= new JEditorPane();
		 CheckTrade = new JCheckBox("Trades",true);
		 CheckRfq = new JCheckBox("Rfqs",true);
		 CheckonlineUP = new JCheckBox("Online Up",true);
		CheckTrade.addItemListener(this);
		CheckRfq.addItemListener(this);
		CheckonlineUP.addItemListener(this);
        Font font = new Font("TimesRoman", Font.ITALIC, 15);
        text.setFont(font);
        text.setForeground(Color.BLACK);
		JScrollPane scrollFrame = new JScrollPane(text);
		text.setAutoscrolls(true);
		scrollFrame.setPreferredSize(new Dimension( 800,300));
        ejecutar.setPreferredSize(new Dimension(100, 50));
        ejecutar.addActionListener(this);
        JPanel panel = new JPanel(new MigLayout());
        panel.add(CheckTrade, "cell 1 0");
        panel.add(CheckRfq, "cell 1 0");
        panel.add(CheckonlineUP, "cell 1 0");
		panel.add(ejecutar, "cell 0 1");
		panel.add(scrollFrame,"cell 1 1");


		setContentPane(panel);
        setTitle("BEX corporation by ®Lizprojects");
        setSize(650, 350);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==ejecutar) {
			ejecutar.setEnabled(false);
			new Thread(new Runnable() {
				@Override
				public void run() {
					SpringApplication.run(BtcaApplication.class);
				}
			}).start();
		}
	
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		if(e.getSource()==CheckTrade) {
			trade=CheckTrade.isSelected();
		}else if(e.getSource()==CheckRfq) {
			rfqs = CheckRfq.isSelected();
		}else if(e.getSource()==CheckonlineUP) {
			subir=CheckonlineUP.isSelected();
		}
	}
}
