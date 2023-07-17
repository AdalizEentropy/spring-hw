package ru.nspk.transaction.storage;

import org.springframework.data.repository.CrudRepository;
import ru.nspk.transaction.model.TransactionHistory;

public interface TransactionHistoryRepository extends CrudRepository<TransactionHistory, Long> {}
