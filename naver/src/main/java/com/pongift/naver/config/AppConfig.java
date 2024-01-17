package com.pongift.naver.config;

import com.pongift.naver.commerce.api.CommerceWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${naver.commerce.host}")
    private String commerceApiHost;

    @Bean
    public CommerceWebClient commerceWebClient() {
        return new CommerceWebClient(commerceApiHost);
    }
}