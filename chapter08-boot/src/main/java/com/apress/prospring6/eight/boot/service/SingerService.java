package com.apress.prospring6.eight.boot.service;

import com.apress.prospring6.eight.boot.entities.Singer;

import java.util.Optional;
import java.util.stream.Stream;

public interface SingerService {

    String ALL_SINGER_NATIVE_QUERY =
            "select ID,FIRST_NAME,LAST_NAME,BIRTH_DATE,VERSION from SINGER";

    Stream<Singer> findAll();

    Stream<Singer> findAllWithAlbum();

    Optional<Singer> findById(Long id);

    void save(Singer singer);

    void delete(Singer singer);

    Stream<Singer> findAllByNativeQuery();

    String findFirstNameById(Long id);
}
