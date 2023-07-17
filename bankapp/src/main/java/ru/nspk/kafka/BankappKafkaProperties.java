package ru.nspk.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties("bankapp.kafka")
public class BankappKafkaProperties {
    private String transTopic;
    private int transPartition;
    private short transReplica;
}
