package ru.nspk.account.storage;

import java.util.Optional;
import ru.nspk.account.model.Account;

public interface AccountStorage {

    Optional<Account> findAccount(long id);

    void updateAccount(Account account);

    void createAccount(Account account);
}
