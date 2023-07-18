package ru.nspk.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nspk.transaction.model.Transaction;

@Slf4j
@Service
public class LogKafkaConsumerService {

    public void accept(Transaction data) {
        log.info("Received data from topic: {}", data.toString());
    }
}
