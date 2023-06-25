package ru.nspk.transaction;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("bankapp.transaction")
public class TransactionProperties {
    private int trxIdPrefix = 10;
    private int trxIdLength = 15;
}
