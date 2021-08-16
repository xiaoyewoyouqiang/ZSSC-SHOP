package com.qf.shopitem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.qf",exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@EnableFeignClients("com.qf.feign")
public class ShopItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopItemApplication.class, args);
	}

}
