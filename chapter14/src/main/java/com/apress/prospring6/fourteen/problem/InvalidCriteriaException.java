package com.apress.prospring6.fourteen.problem;

public class InvalidCriteriaException extends Exception {

    private String fieldName;

    private String messageKey;

    public InvalidCriteriaException(String fieldName, String messageKey) {
        this.fieldName = fieldName;
        this.messageKey = messageKey;
    }

    public String getFieldName(){return fieldName;}

    public String getMessageKey(){return messageKey;}
}
