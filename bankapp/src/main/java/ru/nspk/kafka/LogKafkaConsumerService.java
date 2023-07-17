package ru.nspk.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogKafkaConsumerService implements KafkaConsumerService<String> {

    public void accept(String data) {
        log.info("Received data from topic: {}", data);
    }
}
