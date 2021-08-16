package com.qf.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource2")
public class DataSourceProperties2 extends BaseDateSourceConfig {
    private String aliaes;
}
