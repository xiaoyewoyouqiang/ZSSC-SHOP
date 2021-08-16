package com.qf.shoppay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.qf", exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients("com.qf.feign.api")
public class ShopPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopPayApplication.class, args);
    }

}
