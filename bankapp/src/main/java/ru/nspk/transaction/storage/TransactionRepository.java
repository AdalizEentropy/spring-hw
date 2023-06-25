package ru.nspk.transaction.storage;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.nspk.transaction.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Query(
            "select * from transactions "
                    + "where (account_from = :account or account_to = :account) "
                    + "and transaction_time between :start and :end")
    List<Transaction> findByAccountBetweenDates(
            @Param("account") long account,
            @Param("start") LocalDate start,
            @Param("start") LocalDate end);
}
