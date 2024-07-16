package com.apress.prospring6.three.pickle;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;

import java.util.UUID;

public class PickleAutowiringDemo {
    public static Logger logger = LoggerFactory.getLogger(PickleAutowiringDemo.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(AutowiringCfg.class);
        var target = ctx.getBean(TrickyTarget.class);
        logger.info("target: Created target? {}", target != null);
        logger.info("target: Injected bar?{}", target != null);
        logger.info("target: Injected fooOne? {}", target.fooOne != null ? target.fooOne.toString() : "");
        logger.info("target: Injected fooTwo? {}", target.fooTwo != null ? target.fooTwo.toString() : "");
    }
}


class TrickyTarget {
    private static Logger logger = LoggerFactory.getLogger(TrickyTarget.class);

    Foo fooOne;
    Foo fooTwo;
    Bar bar;

    public TrickyTarget() {
        logger.info(" --> TrickyTarget() called:");
    }

    public TrickyTarget(Foo foo) {
        this.fooOne = foo;
        logger.info(" --> TrickyTarget(Foo) called");
    }

    public TrickyTarget(Foo foo, Bar bar) {
        this.fooOne = foo;
        this.bar = bar;
        logger.info(" --> TrickyTarget(Foo, Bar) called");
    }

    @Autowired
    @Qualifier("fooImplOne")
    public void setFooOne(Foo fooOne){
        this.fooOne = fooOne;
        logger.info(" --> Property fooOne set");
    }

    @Autowired
    @Qualifier("fooImplTwo")
    public void setFooTwo(Foo fooTwo){
        this.fooTwo = fooTwo;
        logger.info(" --> Property fooTwo set");
    }

    @Autowired
    public void setBar(Bar bar){
        this.bar = bar;
        logger.info(" --> Property bar set");
    }
}

interface Foo {


}

class FooImplOne implements Foo {

    String id = "one:" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).toString();
    }

}

class FoolImplTwo implements Foo {

    String id = "two:" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).toString();
    }

}

class Bar {

}

@Configuration
@ComponentScan
class AutowiringCfg {


    @Bean
//    @Primary
    public Foo fooImplOne() {
        return new FooImplOne();
    }

    @Bean
    public Foo fooImplTwo() {
        return new FoolImplTwo();
    }

    @Bean
    public Bar bar() {
        return new Bar();
    }

    @Bean
    public TrickyTarget trickyTarget() {
        return new TrickyTarget();
    }

}
