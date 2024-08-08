package com.apress.prospring6.thirteen;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class Sender {
    private final JmsTemplate jmsTemplate;

    @PostConstruct
    public void init(){ jmsTemplate.setDeliveryDelay(2000L);}

    @Value("${spring.artemis.embedded.queues}")
    private String queueName;

    public void send(Letter letter){
        log.info(" >> sending letter = '{}'",letter);
        jmsTemplate.convertAndSend(queueName,letter);
    }
}
