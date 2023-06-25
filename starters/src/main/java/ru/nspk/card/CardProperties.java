package ru.nspk.card;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("bankapp.card")
public record CardProperties(boolean addCard) {}
