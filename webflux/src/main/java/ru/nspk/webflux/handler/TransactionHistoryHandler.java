package ru.nspk.webflux.handler;

import static org.springframework.web.reactive.function.server.ServerResponse.*;
import static ru.nspk.account.model.AccountStatus.OPEN;

import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.nspk.transaction.dto.TransactionRespDto;
import ru.nspk.webflux.exception.IllegalParamsException;
import ru.nspk.webflux.model.mapper.TransactionMapper;
import ru.nspk.webflux.repository.TransactionReactiveRepository;
import ru.nspk.webflux.service.WebService;

@Component
@RequiredArgsConstructor
public class TransactionHistoryHandler {
    private final TransactionReactiveRepository transactionRepository;
    private final WebService webService;
    private final TransactionMapper mapper;

    public Mono<ServerResponse> getAllHistory() {
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(
                        transactionRepository
                                .findAll()
                                .flatMap(trx -> Mono.justOrEmpty(mapper.toTransactionRespDto(trx))),
                        TransactionRespDto.class);
    }

    public Mono<ServerResponse> getHistoryByAccount(ServerRequest request) {
        final LocalDate startDate = takeLocalDateQueryParam(request, "startDate");
        final LocalDate endDate = takeLocalDateQueryParam(request, "endDate");

        if (startDate == null || endDate == null) {
            return Mono.error(
                    new IllegalParamsException(
                            HttpStatus.BAD_REQUEST, "queryParam could not be empty"));
        }

        return Mono.justOrEmpty(request.pathVariable("accountNumber"))
                .map(Long::valueOf)
                .filterWhen(acct -> webService.getAccountStatus(acct).map(status -> status == OPEN))
                .flatMap(
                        acctNumber ->
                                Mono.from(
                                        transactionRepository.findByAccountBetweenDates(
                                                acctNumber, startDate, endDate)))
                .flatMap(trx -> Mono.justOrEmpty(mapper.toTransactionRespDto(trx)))
                .flatMap(trxDto -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(trxDto))
                .switchIfEmpty(notFound().build())
                .onErrorResume(t -> badRequest().build());
    }

    private LocalDate takeLocalDateQueryParam(ServerRequest request, String paramName) {
        Optional<String> queryParam = request.queryParam(paramName);

        return queryParam.map(LocalDate::parse).orElse(null);
    }
}
