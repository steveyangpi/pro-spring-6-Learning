package com.apress.prospring6.ten.boot.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
//@Table(name = "SINGER")
@NamedQuery(name=Singer.FIND_ALL_WITH_ALBUMS,
        query= """
            select distinct s from Singer s 
            left join fetch s.albums a 
            left join fetch s.instruments i 
            """
)
public class Singer extends AbstractEntity {
    @Serial
    private static final long serialVersionUID = 2L;

    public static final String FIND_ALL_WITH_ALBUMS = "Singer.findAllWithAlbums";

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    @OneToMany(mappedBy = "singer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Album> albums = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "SINGER_INSTRUMENT",
            joinColumns = @JoinColumn(name = "SINGER_ID"),
            inverseJoinColumns = @JoinColumn(name = "INSTRUMENT_ID"))
    private Set<Instrument> instruments = new LinkedHashSet<>();

    public String getFirstName(){return this.firstName;}

    public String getLastname(){return this.lastName;}

    public LocalDate getBirthDate(){return this.birthDate;}

    public Set<Album> getAlbums() {
        return albums;
    }

    public Set<Instrument> getInstruments() {
        return instruments;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean addAlbum(Album album) {
        album.setSinger(this);
        return getAlbums().add(album);
    }

    public void removeAlbum(Album album){getAlbums().remove(album);}

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setInstruments(Set<Instrument> instruments) {
        this.instruments = instruments;
    }

    public boolean addInstrument(Instrument instrument){return getInstruments().add(instrument);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ( o == null || getClass() != o.getClass()) return false;
        Singer singer = (Singer) o;
        if(this.id != null){
            return this.id.equals(((Singer)o).id);
        }
        return firstName.equals(singer.firstName) && lastName.equals(singer.lastName) && birthDate.equals(singer.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName,lastName);
    }

    @Override
    public String toString() {
        return "Singer - Id: " + id + ", First Name: " + firstName
                + ", Last Name: " + lastName + ", Birthday: " + birthDate;
    }
}