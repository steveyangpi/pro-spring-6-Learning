package com.apress.prospring6.three.nesting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChildConfig implements ApplicationContextAware {

    public ApplicationContext applicationContext;

    @Bean
    public TitleProvider childProvider(){
        return TitleProvider.instance("No Such Thing");
    }

    @Bean
    public Song song1(@Value("#{parentProvider.title}")String title){
        return new Song(title);
    }

    @Bean Song song2(@Value("#{childConfig.applicationContext.parent.getBean(\"childProvider\").title}") String title){
        return new Song(title);
    }

    @Bean
    public Song song3(@Value("#{childProvider.title}") String title){
        return new Song(title);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
