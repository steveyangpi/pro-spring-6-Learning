package com.apress.prospring6.seven.base.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "ALBUM")
public class Album extends AbstractEntity {
    @Serial
    private static final long serialVersionUID = 3L;

    private String title;
    private LocalDate releaseDate;

    private Singer singer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SINGER_ID")
    public Singer getSinger() {
        return singer;
    }


    @Column
    public String getTitle() {
        return this.title;
    }

    @Column(name = "RELEASE_DATE")
    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Album album = (Album) o;
        if (album.getId() != null && this.getId() != null){
            return super.equals(o);
        }
        return title.equals(album.title) & releaseDate.equals(album.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),title,releaseDate);
    }

    @Override
    public String toString() {
        return "Album - Id: " + id + ", Singer id: " + (singer !=null ? singer.getId() : "")
                + ", Title: " + title + ", Release Date: " + releaseDate;
    }
}
