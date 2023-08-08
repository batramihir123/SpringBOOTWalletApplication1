package com.HelloSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "com.HelloSpring")
@EnableAspectJAutoProxy
public class GWalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(GWalletApplication.class, args);
	}

}
