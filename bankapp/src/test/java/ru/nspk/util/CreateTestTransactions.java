package ru.nspk.util;

import java.time.LocalDateTime;
import ru.nspk.transaction.dto.TransactionReqDto;
import ru.nspk.transaction.dto.TransactionRespDto;

public class CreateTestTransactions {

    public static TransactionReqDto createDto(long from, long to, double amount, int currency) {
        return TransactionReqDto.builder()
                .accountFrom(from)
                .accountTo(to)
                .amount(amount)
                .currency(currency)
                .build();
    }

    public static TransactionRespDto createRespDto(
            long id, long from, long to, double amount, int currency) {
        return TransactionRespDto.builder()
                .id(id)
                .time(LocalDateTime.now())
                .accountFrom(from)
                .accountTo(to)
                .amount(amount)
                .currency(currency)
                .build();
    }
}
