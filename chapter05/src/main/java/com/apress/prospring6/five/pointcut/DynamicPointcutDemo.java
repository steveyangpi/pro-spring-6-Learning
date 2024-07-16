package com.apress.prospring6.five.pointcut;


import com.apress.prospring6.five.common.GoodGuitarist;
import com.apress.prospring6.five.common.SimpleAroundAdvice;
import com.apress.prospring6.five.common.Singer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

import java.lang.reflect.Method;

class SimpleDynamicPointcut extends DynamicMethodMatcherPointcut{
    private static Logger logger = LoggerFactory.getLogger(SimpleDynamicPointcut.class);

    @Override
    public ClassFilter getClassFilter() {
        return cls -> (cls == GoodGuitarist.class);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        logger.debug("static check for " + method.getName());
        return ("sing".equals(method.getName()));
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
        logger.debug("Dynamic check for " + method.getName());

        if(args.length == 0){
            return false;
        }

        var key = (String) args[0];

        return key.equalsIgnoreCase("C");
    }
}
public class DynamicPointcutDemo {
    public static void main(String[] args) {
        GoodGuitarist target = new GoodGuitarist();
        Advisor advisor = new DefaultPointcutAdvisor(new SimpleDynamicPointcut(),new SimpleAroundAdvice());
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(target);
        pf.addAdvisor(advisor);

        Singer proxy = (Singer) pf.getProxy();

        proxy.sing("C");
        proxy.sing("c");
        proxy.sing("E");

        proxy.sing();
    }
}
