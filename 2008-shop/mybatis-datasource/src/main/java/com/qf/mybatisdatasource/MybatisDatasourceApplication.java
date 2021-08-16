package com.qf.mybatisdatasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.qf",exclude = DataSourceAutoConfiguration.class)
@MapperScan(basePackages = "com.qf.mapper")
public class MybatisDatasourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisDatasourceApplication.class, args);
	}

}
