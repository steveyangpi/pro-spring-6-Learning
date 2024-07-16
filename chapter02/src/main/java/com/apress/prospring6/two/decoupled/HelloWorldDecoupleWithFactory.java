package com.apress.prospring6.two.decoupled;

public class HelloWorldDecoupleWithFactory {
    public static void main(String[] args) {
        MessageRenderer mr = MessageSupportFactory.getInstance().getMessageRenderer()
                .orElseThrow(()->new IllegalArgumentException("Service of type 'MessageRenderer' not found'"));
        MessageProvider mp = MessageSupportFactory.getInstance().getMessageProvider()
                .orElseThrow(()->new IllegalArgumentException("Service of type 'MessageProvider' ws not found!"));

        mr.setMessageProvider(mp);
        mr.render();
    }
}
