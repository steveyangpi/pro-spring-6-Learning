package com.apress.prospring6.five.annotated;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@ComponentScan
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AspectJAopConfig{
}
