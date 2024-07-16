package com.apress.prospring6.seven.base.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SINGER")
@NamedQueries({
        @NamedQuery(name = "Singer.findById",
                query = """
                               select distinct s from Singer s 
                               left join fetch s.albums a 
                               left join fetch s.instruments i 
                               where s.id = :id
                        """),
        @NamedQuery(name = "Singer.findAllWithAlbum",
                query = """
                        select distinct s from Singer s
                        left join fetch s.albums a
                        left join fetch s.instruments i
                            """)
})
public class Singer extends AbstractEntity {
    @Serial
    private static final long serialVersionUID = 2L;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Set<Album> albums = new HashSet<>();
    private Set<Instrument> instruments = new HashSet<>();

    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return this.firstName;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return this.lastName;
    }

    @Column(name = "BIRTH_DATE")
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @OneToMany(mappedBy = "singer", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Album> getAlbums() {
        return albums;
    }

    @ManyToMany
    @JoinTable(name = "SINGER_INSTRUMENT",
            joinColumns = @JoinColumn(name = "SINGER_ID"),
            inverseJoinColumns = @JoinColumn(name = "INSTRUMENT_ID"))
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

    public void removeAlbum(Album album) {
        getAlbums().remove(album);
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setInstruments(Set<Instrument> instruments) {
        this.instruments = instruments;
    }

    public boolean addInstrument(Instrument instrument) {
        return getInstruments().add(instrument);
    }

    @Override
    public String toString() {
        return "Singer - Id: " + id + ", Firt name: " + firstName
                + ", Last name: " + lastName + ", Birthday: " + birthDate;
    }
}
