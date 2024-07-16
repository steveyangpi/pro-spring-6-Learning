package com.apress.prospring6.five.pointcut;

import com.apress.prospring6.five.common.GoodGuitarist;
import com.apress.prospring6.five.common.GreatGuitarist;
import com.apress.prospring6.five.common.SimpleAroundAdvice;
import com.apress.prospring6.five.common.Singer;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

class SimpleStaticPointcut extends StaticMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return ("sing".equals(method.getName()));
    }

    @Override
    public ClassFilter getClassFilter() {
        return cls -> (cls == GoodGuitarist.class);
    }
}

public class StaticPointcutDemo {
    public static void main(String[] args) {
        GoodGuitarist johnMayer = new GoodGuitarist();
        GreatGuitarist ericClapton = new GreatGuitarist();

        Singer proxyOne;
        Singer proxyTwo;

        Pointcut pc = new SimpleStaticPointcut();
        Advice advice = new SimpleAroundAdvice();
        Advisor advisor = new DefaultPointcutAdvisor(pc, advice);

        ProxyFactory pf = new ProxyFactory();
        pf.addAdvisor(advisor);
        pf.setTarget(johnMayer);
        proxyOne  = (Singer) pf.getProxy();

        pf = new ProxyFactory();
        pf.addAdvisor(advisor);
        pf.setTarget(ericClapton);
        proxyTwo = (Singer) pf.getProxy();

        proxyOne.sing();
        proxyTwo.sing();
    }
}
