package ru.nspk.account.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;
import ru.nspk.account.model.Account;

/**
 * @deprecated: will use jdbc
 */
@Component
@Deprecated(forRemoval = true)
public class InMemoryAccountStorage implements AccountStorage {
    private final Map<Long, Account> accounts = new HashMap<>();

    public Optional<Account> findAccount(long id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public void updateAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public void createAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }
}
