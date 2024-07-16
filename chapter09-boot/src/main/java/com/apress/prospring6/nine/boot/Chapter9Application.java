package com.apress.prospring6.nine.boot;

import com.apress.prospring6.nine.boot.services.AllService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class Chapter9Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Chapter9Application.class);

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,"dev");
        var ctx = SpringApplication.run(Chapter9Application.class,args);

        var service = ctx. getBean(AllService.class);

        LOGGER.info(" ---- Listing singer with id=2:");
        var singer = service.findByIdWithAlbums(2L);
        LOGGER.info(singer.toString());
        ctx.close();
    }
}
