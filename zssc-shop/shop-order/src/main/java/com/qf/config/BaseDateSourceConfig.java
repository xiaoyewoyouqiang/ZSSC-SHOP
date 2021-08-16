package com.qf.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;

@Data
public class BaseDateSourceConfig {

    private String driverClassName;

    private String username;

    private String password;

    private String url;


    public HikariDataSource getDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(this.url);
        hikariDataSource.setUsername(this.username);
        hikariDataSource.setPassword(this.password);
        hikariDataSource.setDriverClassName(this.driverClassName);
        return hikariDataSource;
    }

}
