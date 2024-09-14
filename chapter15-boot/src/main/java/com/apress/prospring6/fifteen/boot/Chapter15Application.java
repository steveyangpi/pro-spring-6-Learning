package com.apress.prospring6.fifteen.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;

@Slf4j
@EntityScan(basePackages = {"com.apress.prospring6.fifteen.boot.entities"})
@EnableTransactionManagement
@EnableJpaRepositories("com.apress.prospring6.fifteen.boot.repos")
@SpringBootApplication
public class Chapter15Application {

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,"dev");
        var ctx = SpringApplication.run(Chapter15Application.class,args);

        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(
                bn -> log.info(">> {} : {} ",bn,ctx.getBean(bn).getClass().getName())
        );
    }
}
