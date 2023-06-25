package ru.nspk.transaction.controller;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
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

    @GetMapping
    public List<TransactionRespDto> getAllHistory() {
        return service.getAllHistory();
    }

    @GetMapping("/{accountNumber}")
    public List<TransactionRespDto> getHistory(
            @PathVariable Long accountNumber,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return service.getHistory(accountNumber, startDate, endDate);
    }

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
