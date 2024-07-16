package com.apress.prospring6.four;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentTest {
    private static Logger logger = LoggerFactory.getLogger(EnvironmentTest.class);

    @Test
    void testPropertySourceOne(){
        var ctx = new GenericApplicationContext();

        ConfigurableEnvironment env = ctx.getEnvironment();
        MutablePropertySources propertySources = env.getPropertySources();

        Map<String,Object> appMap = new HashMap<>();
        appMap.put("user.home","CUSTOM_USER_HOME");
        propertySources.addLast(new MapPropertySource("prospring6_MAP",appMap));

        logger.info("-- Env Variables  from java.lang.System --");
        logger.info("user.home: " + System.getProperty("user.home"));
        logger.info("JAVA_HOME: " + System.getenv("JAVA_HOME"));

        logger.info("-- Env Variables  from ConfigurableEnvironment --");
        logger.info("user.home: " + env.getProperty("user.home"));
        logger.info("JAVA_HOME: " + env.getProperty("JAVA_HOME"));

        ctx.close();
    }

    @Test
    void testPropertySourceTwo(){
        var ctx = new GenericApplicationContext();

        ConfigurableEnvironment env = ctx.getEnvironment();
        MutablePropertySources propertySources = env.getPropertySources();

        Map<String,Object> appMap = new HashMap<>();
        appMap.put("user.home", "CUSTOM_USER_HOME");
        propertySources.addFirst(new MapPropertySource("prospring6_MAP", appMap)); // notice the addFirst

        logger.info("-- Env Variables  from java.lang.System --");
        logger.info("user.home: " + System.getProperty("user.home"));
        logger.info("JAVA_HOME: " + System.getenv("JAVA_HOME"));

        logger.info("-- Env Variables  from ConfigurableEnvironment --");
        logger.info("user.home: " + env.getProperty("user.home"));
        logger.info("JAVA_HOME: " + env.getProperty("JAVA_HOME"));

        ctx.close();
    }
}
