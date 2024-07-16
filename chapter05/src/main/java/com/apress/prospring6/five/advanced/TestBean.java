package com.apress.prospring6.five.advanced;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestBean {
    private static Logger LOGGER = LoggerFactory.getLogger(TestBean.class);

    public void foo(){
        LOGGER.info("foo()");
    }
}
