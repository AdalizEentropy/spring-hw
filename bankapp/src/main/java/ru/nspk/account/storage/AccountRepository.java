package ru.nspk.account.storage;

import org.springframework.data.repository.CrudRepository;
import ru.nspk.account.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {}
