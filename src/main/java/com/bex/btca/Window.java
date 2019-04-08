package com.bex.btca;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Window extends JFrame implements ActionListener{
	
	public static JTextArea text;
	JButton ejecutar;
	public static void main(String[] args) {
			Window a =new Window();
			a.start();

	}
	
	public Window() throws HeadlessException {
		super();
		 
	}
	public void start() {
	  	ejecutar = new JButton("Analizar Ficheros");
        text= new JTextArea();
        ejecutar.setPreferredSize(new Dimension(250, 50));
        ejecutar.addActionListener(this);
        JPanel panel = new JPanel(new GridLayout(3,3));
        panel.add(ejecutar);
        panel.add(text);
        setContentPane(panel);
        setTitle("Estadisticass");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==ejecutar) {
			
			SpringApplication.run(BtcaApplication.class);
		}
	
	}


}
