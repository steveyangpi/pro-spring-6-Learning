package com.apress.prospring6.three.valinject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import static java.lang.System.out;

@Component("injectSimple")
public class InjectSimpleDemo {

    @Value("John Mayer")
    private String name;
    @Value("40")
    private int age;
    @Value("1.92")
    private float height;
    @Value("false")
    private boolean developer;
    @Value("1241401112")
    private Long ageInSeconds;

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(InjectSimpleDemo.class);
        ctx.refresh();

        InjectSimpleDemo simple = (InjectSimpleDemo) ctx.getBean("injectSimple");
        out.println(simple);
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n"
                + "Age: " + age + "\n"
                + "Height: " +height +"\n"
                + "Is Developer?: " + developer;
    }
}
