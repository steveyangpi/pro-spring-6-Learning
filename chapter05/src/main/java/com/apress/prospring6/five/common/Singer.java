package com.apress.prospring6.five.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Singer {
    Logger LOGGER = LoggerFactory.getLogger(Singer.class);

    void sing();

    default void sing(String key) {
        LOGGER.info("Singing in the key of {}", key);
    }

    default void rest() {

    }
}
