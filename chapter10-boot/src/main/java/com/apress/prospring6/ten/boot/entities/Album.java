package com.apress.prospring6.ten.boot.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "ALBUM")
@NamedQuery(name=Album.FIND_WITH_RELEASE_DATE_GREATER_THAN,query="select a from Album a where a.releaseDate > ?1")
public class Album extends AbstractEntity {
    @Serial
    private static final long serialVersionUID = 3L;

    public static final String FIND_WITH_RELEASE_DATE_GREATER_THAN = "Album.findWithReleaseDateGreaterThan";

    //@Column
    private String title;
    //@Column(name = "RELEASE_DATE")
    private LocalDate releaseDate;


    @ManyToOne
    @JoinColumn(name = "SINGER_ID")
    private Singer singer;

    public Singer getSinger() {
        return singer;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
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
        Album album = (Album) o;
        if (this.id != null) {
            return this.id.equals(((Album) o).id);
        }
        return title.equals(album.title) && releaseDate.equals(album.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, releaseDate);
    }

    @Override
    public String toString() {
        return "Album - Id: " + id + ", Singer id: " + (singer != null ? singer.getId() : "")
                + ", Title: " + title + ", ReleaseDate: " + releaseDate;
    }
}