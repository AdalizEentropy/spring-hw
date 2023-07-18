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
    private final LogKafkaConsumerService logKafkaConsumerService;
    private final TransHistoryKafkaConsumerService transHistKafkaConsumerService;

    @KafkaListener(
            topics = "${bankapp.kafka.trans-topic}",
            clientIdPrefix = "logConsumer",
            groupId = "bank-app-consumers-for-log",
            containerFactory = "kafkaListenerObjectContainerFactory")
    public void logTransaction(
            ConsumerRecord<Long, Transaction> consumerRecord, @Payload Transaction payload) {
        log.info(
                "Received key for log: {}, Payload: {}, Record: {}",
                consumerRecord.key(),
                payload,
                consumerRecord);
        logKafkaConsumerService.accept(payload);
    }

    @KafkaListener(
            topics = "${bankapp.kafka.trans-topic}",
            clientIdPrefix = "saveConsumer",
            groupId = "bank-app-consumers-for-save",
            containerFactory = "kafkaListenerObjectContainerFactory")
    public void saveTransaction(
            ConsumerRecord<Long, Transaction> consumerRecord, @Payload Transaction payload) {
        log.info(
                "Received key for save: {}, Payload: {}, Record: {}",
                consumerRecord.key(),
                payload,
                consumerRecord);
        transHistKafkaConsumerService.accept(payload);
    }
}
