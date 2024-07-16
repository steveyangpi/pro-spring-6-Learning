package com.apress.prospring6.four.groovy;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Singer {
    private String name;
    private int age;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name",name)
                .append("age",age)
                .toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
