package com.apress.prospring6.four.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class MessageDigester {
    private static Logger LOGGER = LoggerFactory.getLogger(MessageDigester.class);

    private MessageDigest digest1;
    private MessageDigest digest2;

    public void setDigest1(MessageDigest digest1) {
        this.digest1 = digest1;
    }

    public void setDigest2(MessageDigest digest2) {
        this.digest2 = digest2;
    }

    public void digest(String msg) {
        LOGGER.info("Using digest1");
        digest(msg,digest1);

        LOGGER.info("Using digest2");
        digest(msg,digest2);
    }

    private void digest(String msg, MessageDigest digest) {
        LOGGER.info("Using algorithm: " + digest.getAlgorithm());
        digest.reset();
        byte[] bytes = msg.getBytes();
        byte[] out = digest.digest(bytes);

        LOGGER.info("Original Message: {}  ", bytes);
        LOGGER.info("Encrypted Message: {} ", out);
    }
}
