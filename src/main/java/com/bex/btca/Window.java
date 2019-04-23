package com.bex.btca;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Window extends JFrame implements ActionListener{
	
	public static JTextArea text;
	public static JButton ejecutar;

	public static void main(String[] args) {
			Window a =new Window();
			a.start();

	}
	
	public Window() throws HeadlessException {
		super();
		 
	}
	public void start() {
	  	ejecutar = new JButton("Analizar Ficheros");
        text= new JTextArea(5,40);
        Font font = new Font("TimesRoman", Font.ITALIC, 15);
        text.setFont(font);
        text.setForeground(Color.BLACK);        
        
        ejecutar.setPreferredSize(new Dimension(100, 50));
        ejecutar.addActionListener(this);
        JPanel panel = new JPanel(new GridLayout(2,1));
        panel.add(ejecutar);
        panel.add(text);
        setContentPane(panel);
        setTitle("Estadisticass");
        setSize(450, 350);
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


}
