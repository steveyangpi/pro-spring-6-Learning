package com.apress.prospring6.eight.boot.view;

import java.io.Serializable;

public class SingerSummary implements Serializable {
    private static final long serialVersionUID = 5L;
    private String firstName;
    private String lastName;
    private String latestAlbum;

    public SingerSummary(String firstName, String lastName, String latestAlbum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.latestAlbum = latestAlbum;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLatestAlbum() {
        return latestAlbum;
    }

    @Override
    public String toString() {
        return "First name: " + firstName + ", Last Name: " + lastName
                + ", Most Recent Album: " + latestAlbum;
    }
}
