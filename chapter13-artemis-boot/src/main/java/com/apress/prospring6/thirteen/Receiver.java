package com.apress.prospring6.thirteen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Receiver {

    @JmsListener(destination = "${spring.artemis.embedded.queues}")
    public void receive(Letter letter) {
        log.info(" >> received letter='{}'", letter);
    }
}
