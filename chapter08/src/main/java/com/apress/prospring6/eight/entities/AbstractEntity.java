package com.apress.prospring6.eight.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@MappedSuperclass
public class AbstractEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected Long id;
    protected int version;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}