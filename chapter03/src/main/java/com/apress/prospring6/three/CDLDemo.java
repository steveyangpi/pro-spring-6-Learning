package com.apress.prospring6.three;


import static java.lang.System.out;

interface ManagedComponent {
    void performLookup(Container container);
}

interface Container {
    Object getDependency(String key);
}


class DefaultContainer implements Container {
    @Override
    public Object getDependency(String key) {
        if ("provider".equals(key)) {
            return new HelloWorldMessageProvider();
        }

        throw new RuntimeException("Unknown dependency: " + key);
    }
}

public class CDLDemo {

    public static void main(String[] args) {
        Container container = new DefaultContainer();

        MessageRenderer renderer = new StandardOutMessageRenderer();
        renderer.performLookup(container);

        renderer.render();
    }
}

interface MessageProvider {
    String getMessage();
}

class HelloWorldMessageProvider implements MessageProvider {
    public HelloWorldMessageProvider() {
        System.out.println(" --> HelloWorldMessageProvider: constructor called");
    }

    @Override
    public String getMessage() {
        return "Hello World!";
    }
}

interface MessageRenderer extends ManagedComponent {
    void render();
}

class StandardOutMessageRenderer implements MessageRenderer {

    private MessageProvider messageProvider;

    public StandardOutMessageRenderer() {
        out.println("--> StandardOutMessageRenderer: constructor");
    }

    @Override
    public void performLookup(Container container) {
        this.messageProvider = (MessageProvider) container.getDependency("provider");
    }

    @Override
    public String toString() {
        return messageProvider.toString();
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
