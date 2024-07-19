package com.apress.prospring6.ten.boot;

import com.apress.prospring6.ten.boot.service.SingerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class Mongo10Application {

    private static final Logger LoGGER = LoggerFactory.getLogger(Mongo10Application.class);

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,"dev");
        var ctx = SpringApplication.run(Mongo10Application.class,args);
        var service = ctx.getBean(SingerService.class);
        service.findAll().forEach(s -> LoGGER.info(s.toString()));
    }
}
