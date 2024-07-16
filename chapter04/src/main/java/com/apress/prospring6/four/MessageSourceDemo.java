package com.apress.prospring6.four;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

@Configuration
class MessageSourceConfig {

    @Bean
    public MessageSource messageSource() {
        var messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("labels");
        return messageSource;
    }
}

public class MessageSourceDemo {
    private static Logger LOGGER = LoggerFactory.getLogger(MessageSourceDemo.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(MessageSourceConfig.class);

        Locale english = Locale.ENGLISH;

        Locale ukrainian = new Locale("uk", "UA");
        LOGGER.info(ctx.getMessage("msg", null, english));
        LOGGER.info(ctx.getMessage("msg", null, ukrainian));
        LOGGER.info(ctx.getMessage("nameMsg", new Object[]{"Iuliana", "Cosmina"}, english));
        LOGGER.info(ctx.getMessage("nameMsg", new Object[]{"Iuliana", "Cosmina"}, ukrainian));
        ctx.close();
    }
}
