package ru.nspk.webflux;

import static ru.nspk.account.model.AccountStatus.CLOSED;
import static ru.nspk.account.model.AccountStatus.OPEN;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.nspk.transaction.dto.TransactionRespDto;
import ru.nspk.webflux.base.BaseContainerTest;
import ru.nspk.webflux.service.WebService;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {WebfluxApplication.class})
class WebfluxApplicationTest extends BaseContainerTest {

    @Autowired private WebTestClient client;
    @MockBean private WebService webService;
    @MockBean private WebClient webClient;

    @Test
    void getAllTransactions() {
        client.get()
                .uri("/transactions")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(TransactionRespDto.class)
                .hasSize(2);
    }

    @Test
    void notGetTransactions_ByAccountNumber_withoutDate() {
        client.get()
                .uri("/transactions/12345")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("message")
                .isEqualTo("queryParam could not be empty");
    }

    @Test
    void notGetTransactions_ifNotFound() {
        Mockito.when(webService.getAccountStatus(12345)).thenReturn(Flux.just(OPEN));

        client.get()
                .uri(
                        uriBuilder ->
                                uriBuilder
                                        .path("/transactions/12345")
                                        .queryParam("startDate", LocalDate.parse("2000-07-08"))
                                        .queryParam("endDate", LocalDate.parse("2000-07-09"))
                                        .build())
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody()
                .isEmpty();
    }

    @Test
    void getTransactions_ByAccountNumber_whenOpen() {
        Mockito.when(webService.getAccountStatus(12345)).thenReturn(Flux.just(OPEN));

        client.get()
                .uri(
                        uriBuilder ->
                                uriBuilder
                                        .path("/transactions/12345")
                                        .queryParam("startDate", LocalDate.parse("2023-07-08"))
                                        .queryParam("endDate", LocalDate.parse("2023-07-09"))
                                        .build())
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(TransactionRespDto.class)
                .hasSize(1);
    }

    @Test
    void getTransactions_ByAccountNumber_whenClosed() {
        Mockito.when(webService.getAccountStatus(12345)).thenReturn(Flux.just(CLOSED));

        client.get()
                .uri(
                        uriBuilder ->
                                uriBuilder
                                        .path("/transactions/12345")
                                        .queryParam("startDate", LocalDate.parse("2023-07-08"))
                                        .queryParam("endDate", LocalDate.parse("2023-07-09"))
                                        .build())
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody()
                .isEmpty();
    }
}
