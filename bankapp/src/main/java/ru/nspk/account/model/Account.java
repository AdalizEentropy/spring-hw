package ru.nspk.account.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import ru.nspk.card.model.Card;

@Getter
@Setter
@Table("ACCOUNTS")
public class Account implements Persistable<Long> {
    @Id private final long accountNumber;
    private double balance;
    private final int currency;

    @MappedCollection(idColumn = "ACCOUNT_NUMBER")
    private Set<BalanceHistoryRecord> balanceHistoryRecords;

    @Transient private List<Card> cards;
    @Transient private final boolean isNew;

    public Account(long accountNumber, double balance, int currency, boolean isNew) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
        this.cards = new ArrayList<>();
        this.balanceHistoryRecords = new HashSet<>();
        this.isNew = isNew;
    }

    @PersistenceCreator
    public Account(long accountNumber, double balance, int currency) {
        this(accountNumber, balance, currency, false);
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
    public Long getId() {
        return accountNumber;
    }
}
