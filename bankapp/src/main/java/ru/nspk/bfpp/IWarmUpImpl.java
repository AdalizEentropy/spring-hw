package ru.nspk.bfpp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IWarmUpImpl implements IWarmUp {

    @Override
    public void warmUp() {
        log.info("Init method warmUp");
    }
}
