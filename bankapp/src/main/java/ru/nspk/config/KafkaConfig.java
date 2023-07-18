package ru.nspk.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.nspk.kafka.BankappKafkaProperties;
import ru.nspk.transaction.model.Transaction;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(BankappKafkaProperties.class)
public class KafkaConfig {
    private final BankappKafkaProperties properties;
    private final KafkaProperties kafkaProperties;

    @Bean
    public ProducerFactory<Long, Transaction> customKafkaProducerFactory() {
        var props = kafkaProperties.buildConsumerProperties();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<Long, Transaction> kafkaTemplate() {
        return new KafkaTemplate<>(customKafkaProducerFactory());
    }

    @Bean
    public ConsumerFactory<Long, Transaction> objectKafkaConsumerFactory() {
        final JsonDeserializer<Transaction> jsonDeserializer = new JsonDeserializer<>();
        jsonDeserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(
                kafkaProperties.buildConsumerProperties(),
                new LongDeserializer(),
                jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, Transaction>
            kafkaListenerObjectContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, Transaction> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(objectKafkaConsumerFactory());

        return factory;
    }

    @Bean
    public NewTopic transTopic() {
        return new NewTopic(
                properties.getTransTopic(),
                properties.getTransPartition(),
                properties.getTransReplica());
    }
}
