package ru.nspk.transaction.model;

import java.time.LocalDateTime;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@ToString
@Table("transactions")
public class Transaction {
    @Id private final Long id;

    @Column(value = "transaction_time")
    private final LocalDateTime time;

    private final long accountFrom;
    private final long accountTo;
    private final double amount;
    private final int currency;

    @PersistenceCreator
    public Transaction(
            Long id,
            LocalDateTime time,
            long accountFrom,
            long accountTo,
            double amount,
            int currency) {
        this.id = id;
        this.time = time;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.currency = currency;
    }
}
