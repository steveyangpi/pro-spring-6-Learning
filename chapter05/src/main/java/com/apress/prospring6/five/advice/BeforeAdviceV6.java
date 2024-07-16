package com.apress.prospring6.five.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Scope("prototype")
@Aspect("pertarget(targetIdentifier())")
public class BeforeAdviceV6 {
    private static Logger LOGGER = LoggerFactory.getLogger(BeforeAdviceV6.class);

    public BeforeAdviceV6() {
        LOGGER.info("BeforeAdviceV6 creation time: {}", Instant.now());
    }

    @Pointcut("target(com.apress.prospring6.five.common.Singer+))")
    public void targetIdentifier(){

    }

    @Pointcut("execution(* com.apress.prospring6.five..sing*(com.apress.prospring6.five.common.Guitar))")
    public void singExecution(){

    }

    @Before("singExecution()")
    public void singBeforeAdvice(JoinPoint joinPoint){
        var signature = (MethodSignature) joinPoint.getSignature();
        LOGGER.info(" > Executing: {} from {}",signature.getName(),signature.getDeclaringTypeName());
    }
}
