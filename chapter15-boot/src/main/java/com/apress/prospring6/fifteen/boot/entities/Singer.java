package com.apress.prospring6.fifteen.boot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name="SINGER")
public class Singer {
    @Serial
    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="ID")
    protected Long id;

    @Version
    @Column(name="VERSION")
    protected int version;

    @NotEmpty
    @Size(min = 2,max = 30)
    @Column(name="FIRST_NAME")
    private String firstName;

    @NotEmpty
    @Size(min=2,max=30)
    @Column(name="LAST_NAME")
    private String lastName;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name="BIRTH_DATE")
    private LocalDate birthDate;

    public String getFirstName(){return this.firstName;}

    public String getLastName(){return this.lastName;}

    public LocalDate getBirthDate(){return birthDate;}

    public void setFirstName(String firstName){this.firstName = firstName;}

    public void setLastName(String lastName){this.lastName = lastName;}

    public void setBirthDate(LocalDate birthDate){this.birthDate = birthDate;}

    @Override
    public final boolean equals(Object o) {
        if(this==o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Singer singer = (Singer) o;
        if(this.id!=null){
            return this.id.equals(((Singer)o).id);
        }
        return firstName.equals(singer.firstName) && lastName.equals(singer.lastName);
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
