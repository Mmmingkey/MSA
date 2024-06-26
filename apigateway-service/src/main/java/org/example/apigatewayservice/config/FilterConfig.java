package org.example.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;

//@Configuration
public class FilterConfig {
//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        // TODO 추후에 .uri("lb://SECOND-SERVICE") 로도 동작하는지 확인
        return builder.routes()
                .route("first-service",r1 -> r1.path("/first-service/**")
                                .filters(f -> f.addRequestHeader("first-request","first-request-header")
                                                .addResponseHeader("first-response","first-response-header"))
                                .uri("http://127.0.0.1:8081/"))
                .route("second-service",r1 -> r1.path("/second-service/**")
                                .filters(f -> f.addRequestHeader("second-request","second-request-header")
                                                .addResponseHeader("second-response","second-response-header"))
                                .uri("http://127.0.0.1:8082/"))
                .build();
    }
}
