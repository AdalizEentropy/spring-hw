package ru.nspk.account.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("BALANCE_HISTORY")
public record BalanceHistoryRecord(@Id Long id,
                                   Long accountNumber,
                                   @Column(value = "HISTORY_TIME")
                                   LocalDateTime time,
                                   Double balance) {

    public BalanceHistoryRecord(Long accountNumber,
                                LocalDateTime time, Double balance) {
        this(null, accountNumber, time, balance);
    }
}
