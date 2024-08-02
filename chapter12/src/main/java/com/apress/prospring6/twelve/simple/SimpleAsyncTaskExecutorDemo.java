package com.apress.prospring6.twelve.simple;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
class RandomStringPrinter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomStringPrinter.class);

    private final TaskExecutor taskExecutor;

    public RandomStringPrinter(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void executeTask() {
        for (int i = 0; i < 10; ++i) {
            final int index = i;
            taskExecutor.execute(() -> LOGGER.info("{}: {}", index, UUID.randomUUID().toString().substring(0, 8)));
        }
    }
}

@Configuration
@EnableAsync
class AppConfig {
    @Bean
    TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
}

public class SimpleAsyncTaskExecutorDemo {

    public static void main(String[] args) throws IOException {
        try(var ctx = new AnnotationConfigApplicationContext(AppConfig.class, RandomStringPrinter.class)){
            var printer = ctx.getBean(RandomStringPrinter.class);
            printer.executeTask();

            System.in.read();
        }
    }
}
