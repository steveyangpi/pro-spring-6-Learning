package com.apress.prospring6.seventeen.boot.repos;

import com.apress.prospring6.seventeen.boot.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepo extends JpaRepository<Album,Long> {
}
