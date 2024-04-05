package org.example.apigatewayservice.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter(){
        super(LoggingFilter.Config.class);
    }
    @Getter
    @Setter
    public static class Config{
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

    @Override
    public GatewayFilter apply(Config config) {
//        return ((exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpResponse response = exchange.getResponse();
//
//            return chain.filter(exchange).then(Mono.fromRunnable( () -> {
//
//            }));
//        });
        GatewayFilter logginFilter = new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            log.info("LoggingFilter Start MSG -> {}",config.getBaseMessage());
            if(config.isPreLogger()){
                log.info("LoggingFilter Pre REQ id -> {}",request.getId());
            }
            return chain.filter(exchange).then(Mono.fromRunnable( () -> {
                if(config.isPostLogger()){
                    log.info("LoggingFilter Post REQ id -> {}",request.getId());
                }
            }));
        }, Ordered.LOWEST_PRECEDENCE);

        return logginFilter;
    }
}
