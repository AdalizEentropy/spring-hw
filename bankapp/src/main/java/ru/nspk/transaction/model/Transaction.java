package ru.nspk.transaction.model;

import java.time.LocalDateTime;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import ru.nspk.account.model.Account;

@Getter
@Setter
@ToString
@Table("TRANSACTIONS")
public class Transaction implements Persistable<Long> {
    @Id private final long id;
    @Column(value = "TRANSACTION_TIME") private final LocalDateTime time;
    private final long accountFrom;
    private final long accountTo;
    private final double amount;
    private final int currency;
    @Transient private final boolean isNew;

    public Transaction(
            long id,
            LocalDateTime time,
            long accountFrom,
            long accountTo,
            double amount,
            int currency,
            boolean isNew) {
        this.id = id;
        this.time = time;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.currency = currency;
        this.isNew = isNew;
    }

    @PersistenceCreator
    public Transaction(
            long id,
            LocalDateTime time,
            long accountFrom,
            long accountTo,
            double amount,
            int currency) {
        this(id, time, accountFrom, accountTo, amount, currency, false);
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
    public Long getId() {
        return id;
    }
}
