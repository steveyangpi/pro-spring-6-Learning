package com.apress.prospring6.three.naming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;


public class BeanNamingDemo {

    private static Logger logger = LoggerFactory.getLogger(BeanNamingDemo.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(BeanNamingCfg.class);
        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(beanName -> logger.debug(beanName));

        try {
            ctx.getBean(SimpleBean.class);
        }catch (Exception e){
            logger.debug("More beans than expected found. ",e);
        }

        logger.info(" ... All beans of type: {} ",SimpleBean.class.getSimpleName());
        var beans = ctx.getBeansOfType(SimpleBean.class);
        beans.entrySet().forEach(b-> System.out.println(b.getKey()));
    }
}

@Configuration
@ComponentScan
class BeanNamingCfg{
    @Bean
    public SimpleBean anotherSimpleBean(){
        return new SimpleBean();
    }
}

@Component
class SimpleBean{}