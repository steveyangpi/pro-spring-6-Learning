package com.apress.prospring6.four.impl;

import com.apress.prospring6.four.impl.provider.ConfigurableMessageProvider;
import com.apress.prospring6.four.impl.renderer.StandardOutMessageRenderer;
import com.apress.prospring6.two.decoupled.MessageProvider;
import com.apress.prospring6.two.decoupled.MessageRenderer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AllConfig {

    @Profile("dev")
    @Bean
    MessageProvider messageProvider(){
        return new ConfigurableMessageProvider("Text Sample");
    }

    @Bean
    MessageRenderer messageRenderer(){
        MessageRenderer messageRenderer = new StandardOutMessageRenderer();
        messageRenderer.setMessageProvider(messageProvider());
        return messageRenderer;
    }
}
