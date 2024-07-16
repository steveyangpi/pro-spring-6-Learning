package com.apress.prospring6.three.explicit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

public class ExplicitBeanNamingDemo {
    private static Logger logger = LoggerFactory.getLogger(ExplicitBeanNamingDemo.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(BeanNamingCfg.class);
        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(beanName -> logger.debug(beanName));

        var simpleBeans = ctx.getBeansOfType(SimpleBean.class);
        simpleBeans.forEach((k,v) -> {
            var aliases = ctx.getAliases(k);
            if(aliases.length>0){
                logger.debug("Aliases for {}",k);
                Arrays.stream(aliases).forEach(a -> logger.debug("\t {}",a));
            }
        });
    }
}

@Configuration
@ComponentScan
class BeanNamingCfg {

    @Bean("SimpleBeanTwo")
    public SimpleBean simpleBean2() {
        return new SimpleBean();
    }

    @Bean({"SimpleBeanThree", "three", "numero_tres"})
    public SimpleBean simpleBean3() {
        return new SimpleBean();
    }

}

@Component("simpleBeanOne")
class SimpleBean {

}