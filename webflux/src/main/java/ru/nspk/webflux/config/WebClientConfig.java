package ru.nspk.webflux.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class WebClientConfig {

    @Value("${web-client.url}")
    private String url;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(url)
                .clientConnector(
                        new ReactorClientHttpConnector(
                                HttpClient.create(ConnectionProvider.newConnection())))
                .build();
    }
}
