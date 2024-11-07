package com.apress.prospring6.seventeen.problem;

import java.io.Serial;

public class InvalidCriteriaException extends Exception {

    @Serial
    private static final long serialVersionUID = 31L;

    private String fieldName;

    private String messageKey;

    public InvalidCriteriaException(String fieldName, String messageKey) {
        this.fieldName = fieldName;
        this.messageKey = messageKey;
    }

    public String getFieldName() {return fieldName;}

    public String getMessageKey() {return messageKey;}
}
