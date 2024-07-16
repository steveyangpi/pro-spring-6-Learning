package com.apress.prospring6.eight.boot;

import com.apress.prospring6.eight.boot.service.SingerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class Chapter8Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Chapter8Application.class);

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,"dev");
        var ctx = SpringApplication.run(Chapter8Application.class, args);

        var service = ctx.getBean(SingerService.class);

        LOGGER.info(" ---- Listing singer with id=2:");
        var singer = service.findById(2L);
        LOGGER.info(singer.toString());
        ctx.close();
    }
}
