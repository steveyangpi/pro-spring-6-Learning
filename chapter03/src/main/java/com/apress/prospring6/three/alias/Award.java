package com.apress.prospring6.three.alias;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Award {

    String[] prize() default {};
}
