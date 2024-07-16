package com.apress.prospring6.three;

import com.apress.prospring6.two.decoupled.HelloWorldMessageProvider;
import com.apress.prospring6.two.decoupled.MessageProvider;
import com.apress.prospring6.two.decoupled.MessageRenderer;
import com.apress.prospring6.two.decoupled.StandardOutMessageRenderer;
import org.springframework.context.annotation.Bean;

public class HelloWorldConfiguration {

    @Bean
    public MessageProvider provider(){
        return new HelloWorldMessageProvider();
    }

    @Bean
    public MessageRenderer renderer(){
        MessageRenderer renderer =new StandardOutMessageRenderer();
        renderer.setMessageProvider(provider());
        return renderer;
    }
}
