package ru.nspk.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.nspk.transaction.model.Transaction;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionKafkaProducerService implements KafkaProducerService<Transaction> {
    private final KafkaTemplate<Long, Transaction> kafkaTemplate;
    private final BankappKafkaProperties properties;

    public void send(Transaction transaction) {
        kafkaTemplate
                .send(properties.getTransTopic(), transaction.getId(), transaction)
                .whenComplete(
                        (sendResult, ex) -> {
                            if (ex != null) {
                                log.error("Failed to send transaction", ex);
                            } else {
                                var metadata = sendResult.getRecordMetadata();
                                log.info(
                                        "Transaction successfully sent to topic: {}, partition: {}, offset {}",
                                        metadata.topic(),
                                        metadata.partition(),
                                        metadata.offset());
                            }
                        });
    }
}
