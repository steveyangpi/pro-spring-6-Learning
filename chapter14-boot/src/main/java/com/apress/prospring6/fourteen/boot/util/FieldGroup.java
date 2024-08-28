package com.apress.prospring6.fourteen.boot.util;

public enum FieldGroup {
    FIRSTNAME,
    LASTNAME,
    BIRTHDATE;

    public static FieldGroup getField(String field){return  FieldGroup.valueOf(field.toUpperCase());}
}
