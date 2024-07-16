package com.apress.prospring6.six.boot.repo;

import com.apress.prospring6.six.boot.records.Singer;

import java.util.Optional;
import java.util.stream.Stream;

public interface SingerRepo {
    Stream<Singer> findAll();

    Optional<String> findFirstNameById(Long id);
}
