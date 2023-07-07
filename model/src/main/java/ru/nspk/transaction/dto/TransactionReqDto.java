package ru.nspk.transaction.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionReqDto {
    private long accountFrom;
    private long accountTo;
    private double amount;
    private int currency;
}
