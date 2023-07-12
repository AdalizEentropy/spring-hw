package ru.nspk.transaction.storage;

import org.springframework.data.repository.CrudRepository;
import ru.nspk.transaction.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {}
