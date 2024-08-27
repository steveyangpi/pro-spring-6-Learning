package com.apress.prospring6.fourteen.boot.repos;

import com.apress.prospring6.fourteen.boot.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepo extends JpaRepository<Album, Long> {
}
