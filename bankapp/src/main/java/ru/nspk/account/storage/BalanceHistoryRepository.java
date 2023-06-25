package ru.nspk.account.storage;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.nspk.account.model.BalanceHistoryRecord;

public interface BalanceHistoryRepository extends CrudRepository<BalanceHistoryRecord, Long> {

    @Query(
            "select balance from balance_history "
                    + "where account_number = :accountNumber and history_time > :date "
                    + "order by history_time limit 1")
    Optional<Double> findByAccountAfterDate(long accountNumber, LocalDateTime date);

    @Query(
            "select balance from balance_history "
                    + "where account_number = :accountNumber and history_time <= :date "
                    + "order by history_time desc limit 1")
    Optional<Double> findByAccountBeforeOrEqualsDate(long accountNumber, LocalDateTime date);
}
