package com.apress.prospring6.twelve.spring.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncExceptionHandler.class);

    @Override
    public void handleUncaughtException(Throwable t, Method method, Object... obj) {
        LOGGER.error("[{}]: task method '{}' failed because {}", Thread.currentThread(), method.getName(), t.getMessage(), t);
    }
}

@Configuration
@EnableAsync
@ComponentScan
class Async2Config implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        var tpts = new ThreadPoolTaskExecutor();
        tpts.setCorePoolSize(2);
        tpts.setMaxPoolSize(10);
        tpts.setThreadNamePrefix("tpte2-");
        tpts.setQueueCapacity(5);
        tpts.initialize();
        return tpts;
    }

    @Bean
    public AsyncService asyncService() {
        return new AsyncServiceImpl();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }
}

public class Async2Demo {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncDemo.class);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        try (var ctx = new AnnotationConfigApplicationContext(Async2Config.class)) {
            var asyncService = ctx.getBean("asyncService", AsyncService.class);

            for (int i = 0; i < 5; i++) {
                asyncService.asyncTask();
            }

            var result1 = asyncService.asyncWithReturn("John Mayer");
            var result2 = asyncService.asyncWithReturn("Eric Clapton");
            var result3 = asyncService.asyncWithReturn("BB King");
            Thread.sleep(6000);

            LOGGER.info(" >> Result1: " + result1.get());
            LOGGER.info(" >> Result2: " + result2.get());
            LOGGER.info(" >> Result3: " + result3.get());

            System.in.read();
        }
    }
}
