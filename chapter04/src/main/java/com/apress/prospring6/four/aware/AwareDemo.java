package com.apress.prospring6.four.aware;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class NamedSinger implements BeanNameAware {
    private static Logger logger = LoggerFactory.getLogger(NamedSinger.class);
    private String name;

    @Override
    public void setBeanName(String name) {
        this.name = name;
    }

    public void sing() {
        logger.info("Singer " + name + "- sing()");
    }
}

class FileManager {
    private static Logger logger = LoggerFactory.getLogger(FileManager.class);
    private Path file;

    public FileManager() {
        logger.info("Creating bean of type {}", FileManager.class);
        try {
            file = Files.createFile(Path.of("sample"));
        } catch (IOException e) {
            logger.error("Could not create file");
        }
    }

    @PreDestroy
    private void preDestroy() throws IOException {
        logger.info("Calling preDestroy(0 on bean of type {}", FileManager.class);
        if (file != null) {
            Files.deleteIfExists(file);
        }
    }
}

@ComponentScan
class AwareConfig {

    @Bean
    NamedSinger johnMayer() {
        return new NamedSinger();
    }

    @Bean
    FileManager fileManager() {
        return new FileManager();
    }

    @Bean
    ShutdownHookBean shutdownHookBean() {
        return new ShutdownHookBean();
    }
}

class ShutdownHookBean implements ApplicationContextAware {
    private ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        if (ctx instanceof GenericApplicationContext) {
            ((GenericApplicationContext) ctx).registerShutdownHook();
        }
    }
}

public class AwareDemo {

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(AwareConfig.class);
        ctx.registerShutdownHook();

        var singer = ctx.getBean(NamedSinger.class);
        singer.sing();
    }
}
