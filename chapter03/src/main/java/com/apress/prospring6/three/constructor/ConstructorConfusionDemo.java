package com.apress.prospring6.three.constructor;

import com.apress.prospring6.two.decoupled.MessageProvider;
import com.apress.prospring6.two.decoupled.MessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static java.lang.System.out;

public class ConstructorConfusionDemo {

    public static void main(String[] args) {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);
        MessageRenderer mr = ctx.getBean("renderer",MessageRenderer.class);
        mr.render();
    }
}

@Configuration
@ComponentScan
class HelloWorldConfiguration {
}

@Component("provider")
class HelloWorldMessageProvider implements MessageProvider {

    @Override
    public String getMessage() {
        return "Hello World!";
    }
}

@Component("renderer")
class StandardOutMessageRenderer implements MessageRenderer {

    private MessageProvider messageProvider;

//    @Autowired
    public StandardOutMessageRenderer(MessageProvider messageProvider) {
        out.println(" ~~ Injecting dependency using constructor ~~");
        this.messageProvider = messageProvider;
    }

    @Override
    public void setMessageProvider(MessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }

    @Override
    public MessageProvider getMessageProvider() {
        return this.messageProvider;
    }

    @Override
    public void render() {
        if (messageProvider == null) {
            throw new RuntimeException(
                    "You must set the property messageProvider of class:"
                            + StandardOutMessageRenderer.class.getName()
            );
        }
        out.println(messageProvider.getMessage());
    }
}
