package com.qf.config;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpPost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class EsConfig {

    @Value("${es.host}")
    private String esHost;

    @Value("${es.port}")
    private Integer esPort;

    @Bean
    public RestHighLevelClient restClinet() {
        HttpHost httpHost = new HttpHost(esHost, esPort);
        RestClientBuilder restClientBuilder = RestClient.builder(httpHost);

        RestHighLevelClient client = new RestHighLevelClient(restClientBuilder);
        return client;
    }
}
