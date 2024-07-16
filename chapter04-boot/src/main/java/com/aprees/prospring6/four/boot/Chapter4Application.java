package com.aprees.prospring6.four.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class Chapter4Application {
    private static Logger LOGGER = LoggerFactory.getLogger(Chapter4Application.class);

    public static void main(String[] args) {
        var ctx = SpringApplication.run(Chapter4Application.class,args);
        assert(ctx !=null);

        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(LOGGER::info);
    }
}
