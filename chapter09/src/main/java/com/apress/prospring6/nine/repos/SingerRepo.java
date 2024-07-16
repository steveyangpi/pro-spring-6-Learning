package com.apress.prospring6.nine.repos;

import com.apress.prospring6.nine.entities.Singer;

import java.util.Optional;
import java.util.stream.Stream;

public interface SingerRepo {

    Stream<Singer> findAll();

    Optional<Singer> findById(Long id);

    Optional<Singer> findByFirstNameAndLast(String fn, String ln);

    Long countAllSingers();

    Singer save(Singer singer);

}
