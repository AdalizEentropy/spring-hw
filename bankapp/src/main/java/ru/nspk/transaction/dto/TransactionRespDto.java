package ru.nspk.transaction.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionRespDto {
    private long id;
    private LocalDateTime time;
    private long accountFrom;
    private long accountTo;
    private double amount;
    private int currency;
}
