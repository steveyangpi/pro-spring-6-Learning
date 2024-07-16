package com.apress.prospring6.seven.base.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@MappedSuperclass
public abstract class AbstractEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    protected Long id;
    protected int version;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return this.id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AbstractEntity that = (AbstractEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
