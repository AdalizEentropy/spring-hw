package ru.nspk;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestAppConfig {

    @Bean
    public Clock clock() {
        return Clock.fixed(
                LocalDate.of(1991, 8, 23).atStartOfDay().toInstant(ZoneOffset.UTC),
                ZoneId.of("UTC"));
    }
}
