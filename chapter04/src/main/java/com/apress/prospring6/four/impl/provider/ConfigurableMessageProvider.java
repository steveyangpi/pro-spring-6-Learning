package com.apress.prospring6.four.impl.provider;

import com.apress.prospring6.two.decoupled.MessageProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigurableMessageProvider implements MessageProvider {

    private final String message;

    public ConfigurableMessageProvider(@Value("Text Sample") String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
