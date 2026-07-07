package com.frankapp.backendmovie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FaviconConfiguration {

    @Bean
    public RouterFunction<ServerResponse> faviconRouter() {
        return RouterFunctions
                .route(RequestPredicates.GET("/favicon.ico"),
                        request -> ServerResponse.ok().contentType(MediaType.valueOf("image/svg+xml"))
                                .bodyValue(new ClassPathResource("static/favicon.svg")))
                .andRoute(RequestPredicates.GET("/favicon.svg"),
                        request -> ServerResponse.ok().contentType(MediaType.valueOf("image/svg+xml"))
                                .bodyValue(new ClassPathResource("static/favicon.svg")));
    }
}
