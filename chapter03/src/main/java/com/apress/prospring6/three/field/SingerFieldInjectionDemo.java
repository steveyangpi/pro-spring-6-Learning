package com.apress.prospring6.three.field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

public class SingerFieldInjectionDemo {
    public static void main(String[] args) {
        var ctx =new AnnotationConfigApplicationContext();
        ctx.register(Singer.class,Inspiration.class);
        ctx.refresh();

        Singer singerBean = ctx.getBean(Singer.class);
        singerBean.sing();
    }
}

@Component("singer")
class Singer {

    @Autowired
    private Inspiration inspirationBean;

    public void sing() {
        System.out.println("... " + inspirationBean.getLyic());
    }
}

@Component
class Inspiration {
    private String lyic = "I can keep the door cracked open, to left light through";

    public Inspiration(
            @Value("For all my running, I can understand") String lyic) {
        this.lyic = lyic;
    }

    public String getLyic() {
        return lyic;
    }

    public void setLyic(String lyic) {
        this.lyic = lyic;
    }
}