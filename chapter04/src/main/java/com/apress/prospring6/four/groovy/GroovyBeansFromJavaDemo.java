package com.apress.prospring6.four.groovy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;

public class GroovyBeansFromJavaDemo {
    private static Logger logger = LoggerFactory.getLogger(GroovyBeansFromJavaDemo.class);

    public static void main(String[] args) {
        ApplicationContext context =
                new GenericGroovyApplicationContext("classpath:spring/beans.groovy");
        Singer singer = context.getBean("singer",Singer.class);

        logger.info("Singer bean: {}",singer);
    }

}
