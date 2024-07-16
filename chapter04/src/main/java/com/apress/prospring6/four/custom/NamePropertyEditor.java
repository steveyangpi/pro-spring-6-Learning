package com.apress.prospring6.four.custom;


import org.springframework.beans.propertyeditors.PropertiesEditor;

public class NamePropertyEditor extends PropertiesEditor {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String [] name = text.split("\\s");
        setValue(new FullName(name[0],name[1]));
    }
}
