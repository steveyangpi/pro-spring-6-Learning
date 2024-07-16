package com.apress.prospring6.five.common;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleAroundAdvice implements MethodInterceptor {
    private static Logger LOGGER = LoggerFactory.getLogger(SimpleAroundAdvice.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        LOGGER.debug(">> Invoking " + invocation.getMethod().getName());
        Object retVal = invocation.proceed();
        LOGGER.debug(">> Done");
        return retVal;
    }
}
