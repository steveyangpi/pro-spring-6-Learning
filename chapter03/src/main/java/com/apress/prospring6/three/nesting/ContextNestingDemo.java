package com.apress.prospring6.three.nesting;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static java.lang.System.out;

public class ContextNestingDemo {

    public static void main(String[] args) {
        var parentCtx =new AnnotationConfigApplicationContext();
        parentCtx.register(ParentConfig.class);
        parentCtx.refresh();

        var childCtx =new AnnotationConfigApplicationContext();
        childCtx.register(ChildConfig.class);
        childCtx.setParent(parentCtx);
        childCtx.refresh();

        Song song1 = (Song) childCtx.getBean("song1");
        Song song2 = (Song) childCtx.getBean("song2");
        Song song3 = (Song) childCtx.getBean("song3");
        out.println("from parent ctx: " + song1.getTitle());
        out.println("from parent ctx: " + song2.getTitle());
        out.println("form child ctx: " + song3.getTitle());
    }
}
