package org.example.apigatewayservice.config;

import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public InMemoryHttpExchangeRepository getExchange(){
        return new InMemoryHttpExchangeRepository();
    }
}
