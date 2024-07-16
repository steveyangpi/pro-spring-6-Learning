package com.apress.prospring6.six.plain.pojos;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Singer {
    private static final long serialVersionUID = 1L;
    private long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Set<Album> albums;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean addAlbum(Album album) {
        if (albums == null) {
            albums = new HashSet<>();
            albums.add(album);
            return true;
        } else {
            if (albums.contains(album)) {
                return false;
            }
        }
        albums.add(album);
        return true;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    @Override
    public String toString() {
        return "Singer[id=" +
                id +
                ",firstName=" + firstName +
                ",lastName=" + lastName +
                ",birthDate=" + birthDate +
                "]";
    }
}
