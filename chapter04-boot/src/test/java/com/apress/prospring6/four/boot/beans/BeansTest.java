package com.apress.prospring6.four.boot.beans;

import com.aprees.prospring6.four.boot.beans.WithBeansApplication;
import com.apress.prospring6.two.decoupled.MessageProvider;
import com.apress.prospring6.two.decoupled.MessageRenderer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {WithBeansApplication.class})
public class BeansTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    MessageRenderer messageRenderer;

    @Autowired
    MessageProvider messageProvider;

    @Test
    void contextLoaded() {
        assertNotNull(context);
    }

    @Test
    void rendererTest() {
        assertAll("messageTest",
                () -> assertNotNull(messageProvider),
                () -> assertNotNull(messageProvider),
                () -> assertEquals(messageProvider, messageRenderer.getMessageProvider()));

        messageRenderer.render();
    }
}
