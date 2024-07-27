package com.apress.prospring6.eleven.validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@ComponentScan
public class JakartaValidationCfg {
    @Bean
    LocalValidatorFactoryBean validator(){return new LocalValidatorFactoryBean();}
}
