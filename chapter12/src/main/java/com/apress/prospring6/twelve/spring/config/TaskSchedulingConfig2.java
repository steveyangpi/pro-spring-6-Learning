package com.apress.prospring6.twelve.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.util.ErrorHandler;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@ComponentScan(basePackages = {"com.apress.prospring6.twelve.spring"},
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {TaskSchedulingConfig.class, TaskSchedulingConfig3.class, TaskSchedulingConfig4.class})
)
@EnableScheduling
public class TaskSchedulingConfig2 implements SchedulingConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskSchedulingConfig2.class);

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        var tpts = new ThreadPoolTaskScheduler();
        tpts.setPoolSize(3);
        tpts.setThreadNamePrefix("tsc2-");
        tpts.setErrorHandler(new ErrorHandler() {
            @Override
            public void handleError(Throwable t) {

            }
        });
        tpts.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        });
        return tpts;
    }
}
