package com.apress.prospring6.five.advice;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AnnotatedAdvice {
    private static Logger LOGGER = LoggerFactory.getLogger(AnnotatedAdvice.class);


}
