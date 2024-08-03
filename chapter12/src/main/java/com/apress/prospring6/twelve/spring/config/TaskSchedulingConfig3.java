package com.apress.prospring6.twelve.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@ComponentScan(basePackages = {"com.apress.prospring6.twelve.spring"},
        excludeFilters = @ComponentScan.Filter(
                type= FilterType.ASSIGNABLE_TYPE,
                classes = {TaskSchedulingConfig.class,TaskSchedulingConfig2.class,TaskSchedulingConfig4.class}
        ))
@EnableScheduling
public class TaskSchedulingConfig3 {

    @Bean
    TaskScheduler taskScheduler(){
        var tpts = new ThreadPoolTaskScheduler();
        tpts.setPoolSize(3);
        tpts.setThreadNamePrefix("tsc3-");
        return tpts;
    }
}
