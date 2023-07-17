package ru.nspk.kafka;

public interface KafkaConsumerService<T> {

    void accept(T data);
}
