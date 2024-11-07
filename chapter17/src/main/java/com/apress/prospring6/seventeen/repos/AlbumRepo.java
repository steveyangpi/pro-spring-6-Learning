package com.apress.prospring6.seventeen.repos;

import com.apress.prospring6.seventeen.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepo extends JpaRepository<Album,Long> {
}
