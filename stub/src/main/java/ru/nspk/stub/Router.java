package ru.nspk.stub;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@RequiredArgsConstructor
@Configuration
public class Router {
    private final AccountHandler accountHandler;

    @Bean
    public RouterFunction<ServerResponse> accountRouter() {
        return route().GET(
                        "/accounts/status/{accountNumber}",
                        accept(MediaType.APPLICATION_JSON),
                        accountHandler::getAccountStatus)
                .build();
    }
}
