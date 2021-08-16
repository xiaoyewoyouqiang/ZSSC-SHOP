package com.qf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer{

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("toRegisterUserPage").setViewName("registerUser");
        registry.addViewController("toLoginUserPage").setViewName("login");
        registry.addViewController("toInputUsernamePage").setViewName("inputUsername");
        registry.addViewController("toChangePasswordPage").setViewName("changePassword");
    }
}
