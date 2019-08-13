package com.dedu.gatewaymodule.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 由于是定制化过滤器，使用时需new来使用，而不能注入
 */
@Slf4j
public class CustomFilter implements GatewayFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put("BusiStartTime", System.currentTimeMillis());
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long busiStartTime = exchange.getAttribute("BusiStartTime");
            if (null != busiStartTime) {
                Long time = System.currentTimeMillis() - busiStartTime;
                log.info(exchange.getRequest().getURI().getRawPath() + " ====> " + time + "ms");
            }
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
