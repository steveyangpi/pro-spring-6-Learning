package com.apress.prospring6.five;

import com.apress.prospring6.five.common.AdviceRequired;
import com.apress.prospring6.five.common.Guitar;
import com.apress.prospring6.five.common.SimpleAroundAdvice;
import com.apress.prospring6.five.common.Singer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
class AnnotatedGuitarist implements Singer {
    private static Logger LOGGER = LoggerFactory.getLogger(AnnotatedGuitarist.class);

    @Override
    public void sing() {

    }

    @AdviceRequired
    public void sing(Guitar guitar){
        LOGGER.info("play: " + guitar.play());
    }
}
public class AnnotationPointcutDemo {
    public static void main(String[] args) {
        AnnotatedGuitarist johnMayer = new AnnotatedGuitarist();
        AnnotationMatchingPointcut pc = AnnotationMatchingPointcut.forMethodAnnotation(AdviceRequired.class);

        Advisor advisor = new DefaultPointcutAdvisor(pc, new SimpleAroundAdvice());
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(johnMayer);
        pf.addAdvisor(advisor);

        AnnotatedGuitarist proxy = (AnnotatedGuitarist) pf.getProxy();
        proxy.sing(new Guitar());
        proxy.rest();
    }
}
