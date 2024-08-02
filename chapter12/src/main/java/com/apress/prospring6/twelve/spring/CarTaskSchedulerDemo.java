package com.apress.prospring6.twelve.spring;

import com.apress.prospring6.twelve.spring.config.TaskSchedulingConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

import java.io.IOException;

public class CarTaskSchedulerDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarTaskSchedulerDemo.class);

    public static void main(String[] args) throws IOException {
        try(var ctx = initCtx(args)){
            try {
                var taskScheduler = ctx.getBean(ScheduledAnnotationBeanPostProcessor.DEFAULT_TASK_SCHEDULER_BEAN_NAME);
                LOGGER.info(" >>>> 'taskSchudler' found: {}",taskScheduler.getClass());
            }catch (NoSuchBeanDefinitionException nbd){
                LOGGER.debug("No 'taskScheduler' configured!");
            }

            System.in.read();
        }
    }

    private static GenericApplicationContext initCtx(String... args){
        if(args.length == 0){
            return new AnnotationConfigApplicationContext(TaskSchedulingConfig.class);
        }
        return null;
    }
}
