package ru.nspk.webflux.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.nspk.account.model.AccountStatus;

@Service
@RequiredArgsConstructor
public class WebService {
    private final WebClient webClient;

    public Flux<AccountStatus> getAccountStatus(long accountNumber) {
        return webClient
                .get()
                .uri("/accounts/status/" + accountNumber)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(AccountStatus.class));
    }
}
