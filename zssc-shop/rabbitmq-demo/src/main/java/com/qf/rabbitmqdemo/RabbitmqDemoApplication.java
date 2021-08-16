package com.qf.rabbitmqdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.qf")
public class RabbitmqDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqDemoApplication.class, args);
	}

}
