package ru.nspk.webflux.config;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.nspk.webflux.handler.TransactionHistoryHandler;

@RequiredArgsConstructor
@Configuration
public class Router {
    private final TransactionHistoryHandler transactionHistoryHandler;

    @Bean
    public RouterFunction<ServerResponse> transactionHistoryRouter() {
        return route().GET(
                        "/transactions",
                        accept(MediaType.APPLICATION_JSON),
                        req -> transactionHistoryHandler.getAllHistory())
                .GET(
                        "/transactions/{accountNumber}",
                        accept(MediaType.APPLICATION_JSON),
                        transactionHistoryHandler::getHistoryByAccount)
                .build();
    }
}
