package com.apress.prospring6.ten.boot;

import com.apress.prospring6.ten.boot.service.SingerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EntityScan(basePackages = {"com.apress.prospring6.ten.boot.entities"})
@EnableTransactionManagement
@EnableJpaRepositories("com.apress.prospring6.ten.boot.repos")
@SpringBootApplication
public class Chapter10Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Chapter10Application.class);

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,"dev");
        var ctx = SpringApplication.run(Chapter10Application.class,args);
        var service = ctx.getBean(SingerService.class);

        LOGGER.info(" ---- Listing singers:");
        service.findAll().forEach(s -> LOGGER.info(s.toString()));
    }
}
