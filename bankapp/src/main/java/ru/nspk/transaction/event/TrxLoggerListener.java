package ru.nspk.transaction.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TrxLoggerListener {

    @EventListener
    public void processEvent(TrxLoggerEvent event) {
        log.info("Event was received = {}", event.message());
    }
}
