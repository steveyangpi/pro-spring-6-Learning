package com.apress.prospring6.seventeen.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

//@SpringBootApplication/*(exclude = { SecurityAutoConfiguration.class})*/
@SpringBootApplication
public class Chapter17Application {
    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,"dev");
        SpringApplication.run(Chapter17Application.class, args);
    }
}
