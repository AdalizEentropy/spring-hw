package ru.nspk.transaction.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrxLoggerPublisher {
    private final ApplicationEventPublisher publisher;

    public void publishEvent(final String message) {
        publisher.publishEvent(new TrxLoggerEvent(message));
    }
}
