package ru.nspk.kafka;

public interface KafkaProducerService<T> {

    void send(T data);
}
