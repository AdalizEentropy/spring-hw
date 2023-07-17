package ru.nspk.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.nspk.transaction.model.Transaction;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaListeners {
    private final KafkaConsumerService<String> stringKafkaConsumerService;
    private final KafkaConsumerService<Transaction> transactionKafkaConsumerService;

    @KafkaListener(
            topics = "${bankapp.kafka.trans-topic}",
            containerFactory = "kafkaListenerStringContainerFactory")
    public void listenAsString(
            ConsumerRecord<Long, String> consumerRecord, @Payload String payload) {
        log.info(
                "Received key: {}, String payload: {}, Record: {}",
                consumerRecord.key(),
                payload,
                consumerRecord);
        stringKafkaConsumerService.accept(payload);
    }

    @KafkaListener(
            topics = "${bankapp.kafka.trans-topic}",
            containerFactory = "kafkaListenerObjectContainerFactory")
    public void listenAsObject(
            ConsumerRecord<Long, Transaction> consumerRecord, @Payload Transaction payload) {
        log.info(
                "Received key: {}, Object payload: {}, Record: {}",
                consumerRecord.key(),
                payload,
                consumerRecord);
        transactionKafkaConsumerService.accept(payload);
    }
}
