package ru.nspk.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.nspk.card.CardProperties;
import ru.nspk.card.service.CardService;
import ru.nspk.card.service.CardServiceImpl;

@Configuration
@EnableConfigurationProperties(CardProperties.class)
public class StarterAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(CardService.class)
    public CardService cardService(CardProperties properties) {
        return new CardServiceImpl(properties);
    }
}
