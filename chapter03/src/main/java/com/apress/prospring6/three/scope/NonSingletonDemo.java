package com.apress.prospring6.three.scope;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("nonSingleton")
@Scope(scopeName = "prototype")
class Singer {

    private String name = "unknown";

    public Singer(@Value("John Mayer") String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .toString();
    }
}

public class NonSingletonDemo {

    private static Logger logger = LoggerFactory.getLogger(NonSingletonDemo.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(Singer.class);
        ctx.refresh();
        var singer1 = ctx.getBean("nonSingleton", Singer.class);
        var singer2 = ctx.getBean("nonSingleton", Singer.class);

        logger.info("Identity Equal?: " + (singer1 == singer2));
        logger.info("Value Equal:?" + singer1.equals(singer2));

        logger.info(singer1.toString());
        logger.info(singer2.toString());
    }

}
