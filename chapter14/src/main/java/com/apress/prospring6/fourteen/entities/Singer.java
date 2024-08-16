package com.apress.prospring6.fourteen.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "SINGER")
public class Singer extends AbstractEntity {
    @Serial
    private static final long serialVersionUID =2L;


    @NotEmpty
    @Size(min = 2,max = 30)
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotEmpty
    @Size(min = 2,max = 30)
    @Column(name = "Last_Name")
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name= "BIRTH_DATE")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "singer")
    private Set<Album> albums = new HashSet<>();

    @Basic(fetch=FetchType.LAZY)
    @Lob
    @Column(name="PHOTO")
    private byte[] photo;

    @ManyToMany
    @JoinTable(name = "SINGER_INSTRUMENT",
            joinColumns = @JoinColumn(name = "SINGER_ID"),
            inverseJoinColumns = @JoinColumn(name = "INSTRUMENT_ID"))
    private Set<Instrument> instruments = new HashSet<>();

    public Set<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(Set<Instrument> instruments) {
        this.instruments = instruments;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }
}