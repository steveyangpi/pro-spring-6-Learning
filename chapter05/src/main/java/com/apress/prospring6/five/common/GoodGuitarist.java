package com.apress.prospring6.five.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoodGuitarist implements Singer{
    private static Logger LOGGER = LoggerFactory.getLogger(GoodGuitarist.class);

    @Override
    public void sing() {
        LOGGER.info("Head on your heart, arms around me");
    }
}
