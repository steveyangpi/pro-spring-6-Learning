package com.apress.prospring6.three.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

public class BeanNameGeneratorDemo {

    private static Logger logger = LoggerFactory.getLogger(BeanNameGeneratorDemo.class);
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(BeanNamingCfg.class);

        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(beanName -> logger.debug(beanName));
        try{
            var sb = ctx.getBean("simpleBean");
        }catch (NoSuchBeanDefinitionException nsb){
            logger.debug("Bean '{}' could not be found",nsb.getBeanName());
        }
    }
}

@Configuration
@ComponentScan(nameGenerator = SimpleBeanNameGenerator.class)
class BeanNamingCfg {
    @Bean
    public SimpleBean anotherSimpleBean() {
        return new SimpleBean();
    }
}


@Component
class SimpleBean {
}

class SimpleBeanNameGenerator extends AnnotationBeanNameGenerator {
    @Override
    protected String buildDefaultBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        var beanName = definition.getBeanClassName().substring(definition.getBeanClassName().lastIndexOf(".") + 1).toLowerCase(Locale.ROOT);
        var uid = UUID.randomUUID().toString().replace("-", "");
        return beanName + "-" + uid;
    }
}