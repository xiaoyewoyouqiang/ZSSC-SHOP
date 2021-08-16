package com.qf.shopeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaServer
public class ShopEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopEurekaApplication.class, args);
	}

}
