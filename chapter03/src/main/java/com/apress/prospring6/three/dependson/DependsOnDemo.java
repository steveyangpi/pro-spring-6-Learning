package com.apress.prospring6.three.dependson;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

public class DependsOnDemo {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(Singer.class,Guitar.class);
        ctx.refresh();

        Singer johnMayer = ctx.getBean("johnMayer",Singer.class);
        johnMayer.sing();
    }
}

@Component("gopher")
class Guitar{
    public void sing() {
        System.out.println("Cm Eb Fm Ab Bb");
    }
}

@DependsOn("gopher")
@Component("johnMayer")
class Singer implements ApplicationContextAware{
    private ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    private Guitar guitar;

    public Singer(){

    }

    public void sing(){
        guitar =  ctx.getBean("gopher", Guitar.class);
        guitar.sing();
    }
}