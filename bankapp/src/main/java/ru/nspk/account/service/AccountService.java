package ru.nspk.account.service;

import java.time.LocalDate;
import ru.nspk.account.model.Account;
import ru.nspk.account.model.enums.BalanceOperation;
import ru.nspk.card.model.CardProgram;

public interface AccountService {

    void changeBalance(Account account, double amount, BalanceOperation operation);

    Account getAccount(long accountId);

    Double getBalanceByDate(long accountId, LocalDate date);

    void createAccount(Account account);

    Account addCard(Account account, CardProgram program);
}
