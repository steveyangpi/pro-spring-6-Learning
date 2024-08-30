package com.apress.prospring6.fourteen.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EntityScan(basePackages = {"com.apress.prospring6.fourteen.boot.entities"})
@EnableTransactionManagement
@EnableJpaRepositories("com.apress.prospring6.fourteen.boot.repos")
@SpringBootApplication
public class Chapter14Application {

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,"dev");
        SpringApplication.run(Chapter14Application.class,args);
    }
}
