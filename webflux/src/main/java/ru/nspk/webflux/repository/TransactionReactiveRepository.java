package ru.nspk.webflux.repository;

import java.time.LocalDate;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.nspk.webflux.model.Transaction;

@Repository
public interface TransactionReactiveRepository extends ReactiveCrudRepository<Transaction, Long> {

    @Query(
            "select * from transactions "
                    + "where (account_from = :account or account_to = :account) "
                    + "and transaction_time between :start and :end")
    Flux<Transaction> findByAccountBetweenDates(
            @Param("account") long account,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end);
}
