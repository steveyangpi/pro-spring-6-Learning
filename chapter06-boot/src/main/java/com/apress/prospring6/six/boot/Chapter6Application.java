package com.apress.prospring6.six.boot;

import com.apress.prospring6.six.boot.repo.SingerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class Chapter6Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Chapter6Application.class);

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,"dev");
        var ctx = SpringApplication.run(Chapter6Application.class,args);

        var repo = ctx.getBean(SingerRepo.class);
        repo.findAll().forEach(singer -> LOGGER.info(singer.toString()));
    }
}
