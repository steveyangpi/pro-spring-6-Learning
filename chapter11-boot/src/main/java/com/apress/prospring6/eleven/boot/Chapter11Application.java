package com.apress.prospring6.eleven.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class Chapter11Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Chapter11Application.class);

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,"dev");
        var ctx = SpringApplication.run(Chapter11Application.class,args);

        var singerBeanValidationService = ctx.getBean(SingerValidationService.class);
        var singer = new Singer();
        singer.setFirstName("J");
        singer.setLastName("Mayer");
        singer.setGenre(null);
        singer.setGender(null);
        var violations = singerBeanValidationService.validateSinger(singer);
        if(violations.size()!=2){
            LOGGER.info("Unexpected number of violations: {}",violations.size());
        }
        violations.forEach(violation ->
                LOGGER.info("Validation error for property: {} with value: {} with error message: {}",
                        violation.getPropertyPath(),violation.getInvalidValue(),violation.getMessage()));
    }
}
