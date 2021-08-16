package com.qf.shopcar;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.qf")
@EnableEurekaClient
@MapperScan(basePackages = "com.qf.mapper")
public class ShopCarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopCarApplication.class, args);
	}

}
