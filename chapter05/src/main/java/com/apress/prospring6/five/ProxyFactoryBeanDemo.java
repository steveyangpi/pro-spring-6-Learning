package com.apress.prospring6.five;

import com.apress.prospring6.five.common.AuditAdvice;
import com.apress.prospring6.five.common.Documentarist;
import com.apress.prospring6.five.common.GrammyGuitarist;
import org.aopalliance.aop.Advice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AopConfig implements BeanFactoryAware {
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Bean
    public GrammyGuitarist johnMayer() {
        return new GrammyGuitarist();
    }

    @Bean
    public Advice advice() {
        return new AuditAdvice();
    }

    @Bean
    public GrammyGuitarist proxyOne() {
        ProxyFactoryBean pfb = new ProxyFactoryBean();
        pfb.setProxyTargetClass(true);
        pfb.setTarget(johnMayer());
        pfb.setInterceptorNames("advice");
        pfb.setBeanFactory(beanFactory);
        pfb.setFrozen(true);
        return (GrammyGuitarist) pfb.getObject();
    }

    @Bean
    public Documentarist documentaristOne() {
        Documentarist documentarist = new Documentarist();
        documentarist.setDep(proxyOne());
        return documentarist;
    }

    @Bean
    public GrammyGuitarist proxyTwo() {
        ProxyFactoryBean pfb = new ProxyFactoryBean();
        pfb.setProxyTargetClass(true);
        pfb.setTarget(johnMayer());
        pfb.setInterceptorNames("advisor");
        pfb.setBeanFactory(beanFactory);
        pfb.setFrozen(true);
        return (GrammyGuitarist) pfb.getObject();
    }

    @Bean
    public Documentarist documentaristTwo(){
        Documentarist documentarist = new Documentarist();
        documentarist.setDep(proxyTwo());
        return documentarist;
    }

    @Bean
    public DefaultPointcutAdvisor advisor(){
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setAdvice(advice());
        AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
        pc.setExpression("execution(* sing*(..))");
        advisor.setPointcut(pc);
        return advisor;
    }
}

public class ProxyFactoryBeanDemo {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(AopConfig.class);

        Documentarist documentaristOne =
                ctx.getBean("documentaristOne",Documentarist.class);
        Documentarist documentaristTwo =
                ctx.getBean("documentaristTwo",Documentarist.class);
        System.out.println("Documentarist One");
        documentaristOne.execute();

        System.out.println("\n Documentarist Two >> ");
        documentaristTwo.execute();
    }
}
