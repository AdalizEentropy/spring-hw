package ru.nspk.transaction.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("transaction_history")
public record TransactionHistory(
        @Id Long id,
        Long transactionId,
        @Column(value = "history_time") LocalDateTime historyTime) {

    public TransactionHistory(Long transactionId, LocalDateTime historyTime) {
        this(null, transactionId, historyTime);
    }
}
