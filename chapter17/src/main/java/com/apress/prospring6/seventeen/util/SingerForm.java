package com.apress.prospring6.seventeen.util;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class SingerForm {

    @NotEmpty(message = "{NotEmpty.singer.firstName}")
    @Size(min=2,max=30,message = "{Size.singer.firstName}")
    private String firstName;

    @NotEmpty(message = "{NotEmpty.singer.firstName}")
    @Size(min=2,max=30,message = "{Size.singer.firstName}")
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    MultipartFile file;

    public String  getFirstName(){return firstName;}

    public void setFirstName(String firstName){this.firstName = firstName;}

    public String getLastName(){return lastName;}

    public void setLastName(String lastName){this.lastName = lastName;}

    public LocalDate getBirthDate(){return birthDate;}

    public void setBirthDate(LocalDate birthDate){this.birthDate = birthDate;}

    public MultipartFile getFile(){return file;}

    public void setFile(MultipartFile file){this.file = file;}
}
