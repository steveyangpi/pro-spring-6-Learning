package com.apress.prospring6.four.multipe;


import com.apress.prospring6.two.decoupled.MessageProvider;
import com.apress.prospring6.two.decoupled.MessageRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Service;

@Service("provider")
class ConfigurableMessageProvider implements MessageProvider {

    private final String message;

    public ConfigurableMessageProvider(@Value("Love on the weekend") String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}

class StandardOutMessageRenderer implements MessageRenderer {
    private static Logger LOGGER = LoggerFactory.getLogger(StandardOutMessageRenderer.class);

    private MessageProvider messageProvider;

    @Override
    public void setMessageProvider(MessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }

    @Override
    public MessageProvider getMessageProvider() {
        return messageProvider;
    }

    @Override
    public void render() {
        LOGGER.info(messageProvider.getMessage());
    }
}

@Configuration
@ComponentScan
class ServiceConfig {

}

@Configuration
@Import(ServiceConfig.class)
class TheOtherConfig {

    @Autowired
    MessageProvider provider;

    @Bean(name = "messageRenderer")
    public MessageRenderer messageRenderer(){
        MessageRenderer renderer = new StandardOutMessageRenderer();
        renderer.setMessageProvider(provider);
        return renderer;
    }

}

public class ImportDemo {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(TheOtherConfig.class);
        MessageRenderer mr = ctx.getBean("messageRenderer",MessageRenderer.class);
        mr.render();
    }
}
