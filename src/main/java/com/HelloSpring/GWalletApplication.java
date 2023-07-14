package com.HelloSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GWalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(GWalletApplication.class, args);
	}

}
