package ru.nspk.transaction.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.nspk.transaction.dto.TransactionReqDto;
import ru.nspk.transaction.dto.TransactionRespDto;
import ru.nspk.transaction.service.TransactionService;

@RestController
@RequestMapping(path = "/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionRespDto createTransaction(
            @NotNull @RequestBody TransactionReqDto transactionDto) {
        return service.createTransaction(transactionDto);
    }

    @PostMapping("/reverse/{transactionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionRespDto reverseTransaction(@PathVariable Long transactionId) {
        return service.reverseTransaction(transactionId);
    }
}
