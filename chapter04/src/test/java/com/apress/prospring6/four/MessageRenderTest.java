package com.apress.prospring6.four;

import com.apress.prospring6.two.decoupled.MessageProvider;
import com.apress.prospring6.two.decoupled.MessageRenderer;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class MessageRenderTest {

    @Test
    void testStandardOutMessageRender(){
        MessageProvider mockProvider = mock(MessageProvider.class);
        when(mockProvider.getMessage()).thenReturn("test message");

        MessageRenderer messageRenderer = new StandardOutMessageRenderer();
        messageRenderer.setMessageProvider(mockProvider);

        messageRenderer.render();
        verify(mockProvider,times(1)).getMessage();
    }
}
