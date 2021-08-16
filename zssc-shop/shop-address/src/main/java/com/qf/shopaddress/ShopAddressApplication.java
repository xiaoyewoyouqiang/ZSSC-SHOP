package com.qf.shopaddress;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.qf")
@EnableEurekaClient
@MapperScan(basePackages = "com.qf.mapper")
public class ShopAddressApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopAddressApplication.class, args);
	}

}
