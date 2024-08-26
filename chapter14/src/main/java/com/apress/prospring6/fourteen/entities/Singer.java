package com.apress.prospring6.fourteen.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
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
    @Column(name = "LAST_NAME")
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

    public String getFirstName(){return this.firstName;}

    public String getLastName(){return this.lastName;}

    public LocalDate getBirthDate(){return birthDate;}

    public Set<Album> getAlbums(){return albums;}

    public Set<Instrument> getInstruments() {
        return instruments;
    }

    public void setFirstName(String firstName){this.firstName= firstName;}

    public void setLastName(String lastName){this.lastName = lastName;}

    public boolean addAlbum(Album album){
        album.setSinger(this);
        return getAlbums().add(album);
    }

    public void removeAlbum(Album album){getAlbums().remove(album);}

    public void setAlbums(Set<Album> albums){this.albums = albums;}

    public void setBirthDate(LocalDate birthDate){this.birthDate = birthDate;}

    public void setInstruments(Set<Instrument> instruments){this.instruments = instruments;}

    public boolean addInstrument(Instrument instrument){return getInstruments().add(instrument);}

    public byte[] getPhoto(){return photo;}

    public void setPhoto(byte[] photo){this.photo = photo;}

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass()!= o.getClass()) return false;
        Singer singer = (Singer) o;
        if(this.id!=null){
            return this.id.equals(((Singer)o).id);
        }
        return firstName.equals(singer.firstName)&&lastName.equals(singer.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName,lastName);
    }

    @Override
    public String toString() {
        return "Singer - Id: " + id + ", First name: " + firstName
                + ", Last name: " + lastName + ", Birthday: " + birthDate;
    }
}