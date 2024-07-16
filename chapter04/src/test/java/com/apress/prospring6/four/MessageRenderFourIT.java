package com.apress.prospring6.four;

import com.apress.prospring6.four.impl.AllConfig;
import com.apress.prospring6.two.decoupled.MessageProvider;
import com.apress.prospring6.two.decoupled.MessageRenderer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

class TestMessageProvider implements MessageProvider {

    private final String message;

    public TestMessageProvider(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

@Configuration
class TestConfig {
    @Profile("test")
    @Bean
    MessageProvider messageProvider() {
        return new TestMessageProvider("Test Message");
    }
}

@ActiveProfiles("test")
@SpringJUnitConfig(classes = {AllConfig.class, TestConfig.class})
public class MessageRenderFourIT {

    @Autowired
    MessageRenderer messageRenderer;

    @Autowired
    MessageProvider messageProvider;

    @Test
    void testConfig() {

        assertAll("messsageTest",
                () -> assertNotNull(messageRenderer),
                () -> assertNotNull(messageProvider),
                () -> assertTrue(messageProvider instanceof TestMessageProvider),
                () -> assertEquals(messageProvider, messageRenderer.getMessageProvider())
        );

        messageRenderer.render();
    }
}
