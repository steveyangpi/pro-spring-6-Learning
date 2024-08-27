package com.apress.prospring6.fourteen.boot.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "INSTRUMENT")
public class Instrument implements Serializable {
    private static final long serialVersionUID = 4L;
    @Id
    @Column(name = "INSTRUMENT_ID")
    private String instrumentId;

    @ManyToMany
    @JoinTable(name = "SINGER_INSTRUMENT",
        joinColumns = @JoinColumn(name = "INSTRUMENT_ID"),
        inverseJoinColumns = @JoinColumn(name = "SINGER_ID"))
    private Set<Singer> singers = new HashSet<>();

    public String getInstrumentId(){return this.instrumentId;}

    public Set<Singer> getSingers(){return this.singers;}

    public void setSingers(Set<Singer> singers){this.singers = singers;}

    public void setInstrumentId(String instrumentId){this.instrumentId = instrumentId;}

    @Override
    public String toString() {return "Instrument :" + getInstrumentId();}
}