package com.qf.config;

import com.qf.interceptor.LimitIntercerptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private LimitIntercerptor limitIntercerptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 设置拦截器拦截的路径
        registry.addInterceptor(limitIntercerptor).addPathPatterns("/**");
    }
}
