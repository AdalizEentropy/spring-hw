package ru.nspk.webflux;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import ru.nspk.transaction.dto.TransactionRespDto;
import ru.nspk.webflux.base.BaseContainerTest;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = {WebfluxApplication.class},
        properties = {
                "server.port=1212",
        })
class WebfluxApplicationTest extends BaseContainerTest {

    @Autowired private WebTestClient client;

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
    void getTransactions_ByAccountNumber() {
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
}
