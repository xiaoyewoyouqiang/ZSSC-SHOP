package com.qf.shopemail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.qf",exclude = DataSourceAutoConfiguration.class)
public class ShopEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopEmailApplication.class, args);
	}

}
