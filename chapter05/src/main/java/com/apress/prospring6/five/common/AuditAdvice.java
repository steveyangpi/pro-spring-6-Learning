package com.apress.prospring6.five.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class AuditAdvice implements MethodBeforeAdvice {
    private static Logger LOGGER = LoggerFactory.getLogger(AuditAdvice.class);

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        LOGGER.info("Executing {}",method);
    }
}
