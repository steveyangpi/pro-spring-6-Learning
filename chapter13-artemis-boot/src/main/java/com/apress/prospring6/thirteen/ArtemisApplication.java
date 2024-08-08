package com.apress.prospring6.thirteen;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootApplication
@Slf4j
public class ArtemisApplication {
    @Bean
    public MessageConverter messageConverter() {
        var converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        var mapper = new JsonMapper();
        mapper.registerModule(new JavaTimeModule());
        converter.setObjectMapper(mapper);
        return converter;
    }

    public static void main(String[] args) {
        try (var ctx = SpringApplication.run(ArtemisApplication.class, args)) {
            var sender = ctx.getBean(Sender.class);
            for (int i = 0; i < 10; ++i) {
                var letter = new Letter("Letter no. " + i, "Test", LocalDate.now(), UUID.randomUUID().toString());
                sender.send(letter);
            }
            System.in.read();
        }catch (IOException e){
            log.error("Problem reading keystrokes.");
        }
    }
}
