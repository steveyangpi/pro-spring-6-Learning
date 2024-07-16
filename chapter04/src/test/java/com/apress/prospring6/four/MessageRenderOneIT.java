package com.apress.prospring6.four;

import com.apress.prospring6.four.impl.provider.ProviderConfig;
import com.apress.prospring6.four.impl.renderer.RendererConfig;
import com.apress.prospring6.two.decoupled.MessageProvider;
import com.apress.prospring6.two.decoupled.MessageRenderer;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

public class MessageRenderOneIT {

    @Test
    void testConfig(){
        var ctx = new AnnotationConfigApplicationContext(RendererConfig.class, ProviderConfig.class);

        var messageProvider = ctx.getBean(MessageProvider.class);
        var messageRenderer = ctx.getBean(MessageRenderer.class);

        assertAll( "messageTest" ,
                () -> assertNotNull(messageRenderer),
                () -> assertNotNull(messageProvider),
                () -> assertEquals(messageProvider, messageRenderer.getMessageProvider())
        );
        messageRenderer.render();
    }
}
