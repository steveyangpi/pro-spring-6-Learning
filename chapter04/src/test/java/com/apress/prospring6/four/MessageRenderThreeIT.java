package com.apress.prospring6.four;

import com.apress.prospring6.four.impl.provider.ProviderConfig;
import com.apress.prospring6.four.impl.renderer.RendererConfig;
import com.apress.prospring6.two.decoupled.MessageProvider;
import com.apress.prospring6.two.decoupled.MessageRenderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RendererConfig.class, ProviderConfig.class})
public class MessageRenderThreeIT {

    @Autowired
    MessageRenderer messageRenderer;

    @Autowired
    MessageProvider messageProvider;

    @Test
    void testProvider() { assertNotNull(messageProvider);}

    @Test
    void testRenderer(){
        assertAll("messageTest",
                () -> assertNotNull(messageProvider),
                () -> assertNotNull(messageRenderer.getMessageProvider()));
        messageRenderer.render();
    }
}
