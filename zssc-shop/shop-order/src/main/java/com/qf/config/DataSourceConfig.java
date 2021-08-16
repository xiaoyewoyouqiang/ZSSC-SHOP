package com.qf.config;

import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Autowired
    private DataSourceProperties1 dataSourceProperties1;

    @Autowired
    private DataSourceProperties2 dataSourceProperties2;

    @Bean
    public HikariDataSource dataSource1() {
        return dataSourceProperties1.getDataSource();
    }

    @Bean
    public HikariDataSource dataSource2() {
        return dataSourceProperties2.getDataSource();
    }


    // 把上面的dataSource1和dataSource2放入到一个Map(DataSource)中
    @Bean
    public DynamicDataSourceConfig dynamicDataSourceConfig() {

        // 1.创建一个动态的数据源配置
        DynamicDataSourceConfig dataSourceConfig = new DynamicDataSourceConfig();

        // 2、设置默认的数据源
        dataSourceConfig.setDefaultTargetDataSource(dataSource1());

        // 3.把dataSource1和dataSource2放入到一个Mapz中
        Map<Object, Object> map = new HashMap<>();
        map.put(dataSourceProperties1.getAliaes(), dataSource1());
        map.put(dataSourceProperties2.getAliaes(), dataSource2());

        // 4.把Map放入到动态数据源
        dataSourceConfig.setTargetDataSources(map);

        return dataSourceConfig;
    }

    // Spring整合MyBatsPlus
    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() throws IOException {

        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dynamicDataSourceConfig());
        mybatisSqlSessionFactoryBean.setTypeAliasesPackage("com.qf.entity");
        mybatisSqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return mybatisSqlSessionFactoryBean;
    }
}
