package com.apress.prospring6.fourteen.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@MappedSuperclass
public class AbstractEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    protected Long id;

    @Version
    @Column(name="VERSION")
    protected int version;

    public Long getId(){return this.id;}

    public void setId(Long id){this.id = id;}

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}