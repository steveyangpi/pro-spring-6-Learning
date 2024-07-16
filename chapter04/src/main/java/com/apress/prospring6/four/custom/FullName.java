package com.apress.prospring6.four.custom;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class FullName {
    private String firstName;
    private String lastName;

    public FullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("firstName",firstName)
                .append("lastName",lastName)
                .toString();
    }
}
