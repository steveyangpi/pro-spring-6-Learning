package com.apress.prospring6.five;

import com.apress.prospring6.five.introduction.Contact;
import com.apress.prospring6.five.introduction.IsModified;
import com.apress.prospring6.five.introduction.IsModifiedAdvisor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

@Configuration
class IntroductionAopConfig {

    @Bean
    public Contact guitarist() {
        var contact = new Contact();
        contact.setName("John Mayer");
        return contact;
    }

    @Bean
    public IsModifiedAdvisor advisor() {
        return new IsModifiedAdvisor();
    }

    @Bean
    public Contact proxy() {
        ProxyFactoryBean pfb = new ProxyFactoryBean();
        pfb.setProxyTargetClass(true);
        pfb.setTarget(guitarist());
        pfb.addAdvisor(advisor());
        pfb.setFrozen(true);
        return (Contact) pfb.getObject();
    }
}

public class PsbIntroductionDemo {
    private static Logger LOGGER = LoggerFactory.getLogger(PsbIntroductionDemo.class);

    public static void main(String[] args) {
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(IntroductionAopConfig.class);

        Contact proxy = (Contact) ctx.getBean("proxy");
        IsModified proxyInterface = (IsModified) proxy;

        LOGGER.info("Is Contact? => {}",(proxy instanceof Contact));
        LOGGER.info("Is IsModified? => {}",(proxy instanceof IsModified));
        LOGGER.info("Has been modified? => {}",proxyInterface.isModified());

        proxy.setName("John Mayer");
        LOGGER.info("Has been modified? => {}",proxyInterface.isModified());

        proxy.setName("Ben Barnes");
        LOGGER.info("Has been modified? => {}",proxyInterface.isModified());
    }
}
