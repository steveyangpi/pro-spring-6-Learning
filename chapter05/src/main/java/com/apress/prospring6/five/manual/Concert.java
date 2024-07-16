package com.apress.prospring6.five.manual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.time.Duration.ofMillis;

public class Concert implements Performance{
    private static Logger LOGGER = LoggerFactory.getLogger(Concert.class);

    @Override
    public void execute() {
        LOGGER.info(" ... La la la la la laaaa ...");
        try {
            Thread.sleep(ofMillis(2000).toMillis());
        }catch (InterruptedException e){

        }
    }
}
