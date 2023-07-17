package ru.nspk.kafka;

import java.time.Clock;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nspk.transaction.model.Transaction;
import ru.nspk.transaction.model.TransactionHistory;
import ru.nspk.transaction.storage.TransactionHistoryRepository;

@Service
@RequiredArgsConstructor
public class TransHistoryKafkaConsumerService implements KafkaConsumerService<Transaction> {
    private final TransactionHistoryRepository repository;
    private final Clock clock;

    public void accept(Transaction data) {
        repository.save(new TransactionHistory(data.getId(), LocalDateTime.now(clock)));
    }
}
