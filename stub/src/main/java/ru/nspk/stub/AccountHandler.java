package ru.nspk.stub;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.nspk.account.model.AccountStatus;

@Component
@RequiredArgsConstructor
public class AccountHandler {

    public Mono<ServerResponse> getAccountStatus(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(AccountStatus.randomAccountStatus()), AccountStatus.class);
    }
}
