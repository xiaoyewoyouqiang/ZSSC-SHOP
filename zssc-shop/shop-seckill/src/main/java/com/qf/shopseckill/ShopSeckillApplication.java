package com.qf.shopseckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.qf")
@MapperScan(basePackages = "com.qf.mapper")
public class ShopSeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopSeckillApplication.class, args);
    }

}
