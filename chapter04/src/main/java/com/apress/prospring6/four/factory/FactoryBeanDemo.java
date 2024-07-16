package com.apress.prospring6.four.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.security.MessageDigest;

@Configuration
@ComponentScan
class MessageDigestConfig{

    @Bean
    public MessageDigestFactoryBean shaDigest(){
        MessageDigestFactoryBean shaDigest = new MessageDigestFactoryBean();
        shaDigest.setAlgorithmName("SHA1");
        return shaDigest;
    }

    @Bean
    public MessageDigestFactoryBean defaultDigest(){
        return new MessageDigestFactoryBean();
    }

    @Bean
    public MessageDigester digester() throws Exception{
        MessageDigester messageDigester = new MessageDigester();
        messageDigester.setDigest1(shaDigest().getObject());
        messageDigester.setDigest2(defaultDigest().getObject());
        return messageDigester;
    }
}

public class FactoryBeanDemo {
    private static Logger LOGGER = LoggerFactory.getLogger(FactoryBeanDemo.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(MessageDigestConfig.class);
        MessageDigester digester = ctx.getBean("digester",MessageDigester.class);
        digester.digest("Hello World!");

        LOGGER.debug("-----------------------------");

        MessageDigestFactoryBean factoryBean =
                (MessageDigestFactoryBean) ctx.getBean("&shaDigest");

        try {
            MessageDigest shaDigest = factoryBean.getObject();
            LOGGER.info("Explicit use digest bean: {}",shaDigest.digest("Hello World!".getBytes()));
        }catch (Exception ex){
            LOGGER.error("Could not find MessageDigestFactoryBean",ex);
        }

        ctx.close();
    }

}
