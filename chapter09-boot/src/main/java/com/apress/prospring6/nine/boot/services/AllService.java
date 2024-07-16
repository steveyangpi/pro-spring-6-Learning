package com.apress.prospring6.nine.boot.services;

import com.apress.prospring6.nine.boot.entities.Album;
import com.apress.prospring6.nine.boot.entities.Singer;
import com.apress.prospring6.nine.boot.ex.TitleTooLongException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface AllService {

    Optional<Singer> findByIdWithAlbums(Long id);

    Stream<Singer> findAllWithAlbums();

    void update(Singer singer);

    void saveSingerWithAlbums(Singer s, Set<Album> albums) throws TitleTooLongException;

    Long countSingers();
}
