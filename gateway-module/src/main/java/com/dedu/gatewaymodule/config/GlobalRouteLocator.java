package com.dedu.gatewaymodule.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 可使用yml配置文件配置，或者使用这种JavaConfig方式配置
 */
@Configuration
public class GlobalRouteLocator {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("first", r -> r.host("*.localhost*").and().path("/login")
                    .filters(f -> f
                            //进行定制化过滤器绑定
                            .filter(new CustomFilter())
                            .addRequestHeader("Token", "1234533sdfvc15")
                        )
                    .uri("http://localhost:9010")
                )
                .route("two", r -> r.path("/login")
                    .filters(f -> f
                            .filter(new CustomFilter())
                            .addRequestHeader("Token", "1234533sdfvc15"))
                    .uri("http://localhost:9010")
                )
                .build();
    }
}
