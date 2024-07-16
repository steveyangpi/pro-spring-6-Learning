package com.apress.prospring6.five.manual;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

public class ManualAdviceDemo {

    public static void main(String[] args) {
        Concert concert = new Concert();

        ProxyFactory pf = new ProxyFactory();
        pf.addAdvice(new SimpleBeforeAdvice());
        pf.addAdvice(new SimpleAfterAdvice());
        pf.addAdvice(new SimpleAroundAdvice());
        pf.setTarget(concert);

        Performance proxy = (Performance) pf.getProxy();

        proxy.execute();
    }
}

class SimpleBeforeAdvice implements MethodBeforeAdvice {
    private static Logger LOGGER = LoggerFactory.getLogger(SimpleBeforeAdvice.class);

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        LOGGER.info("Before: set up concert hall.");
    }
}

class SimpleAfterAdvice implements AfterReturningAdvice {
    private static Logger LOGGER = LoggerFactory.getLogger(SimpleAfterAdvice.class);

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        LOGGER.info("After: offer standing ovation.");
    }
}

class SimpleAroundAdvice implements MethodInterceptor {
    private static Logger LOGGER = LoggerFactory.getLogger(SimpleAroundAdvice.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        LOGGER.info("Around: starting timer");
        StopWatch sw = new StopWatch();
        sw.start(invocation.getMethod().getName());
        Object returnValue = invocation.proceed();
        sw.stop();
        LOGGER.info("Around: concert duration = {}", sw.getTotalTimeMillis());
        return returnValue;
    }
}