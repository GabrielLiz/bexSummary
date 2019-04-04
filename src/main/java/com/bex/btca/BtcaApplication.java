package com.bex.btca;

import java.net.URL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BtcaApplication {
 public static	URL url = BtcaApplication.class.getProtectionDomain().getCodeSource().getLocation();

	public static void main(String[] args) {
		SpringApplication.run(BtcaApplication.class, args);
	}

	
	
}
