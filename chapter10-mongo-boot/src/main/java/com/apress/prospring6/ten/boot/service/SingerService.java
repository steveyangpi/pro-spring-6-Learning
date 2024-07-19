package com.apress.prospring6.ten.boot.service;

import com.apress.prospring6.ten.boot.document.Singer;

import java.util.stream.Stream;

public interface SingerService {

    Stream<Singer> findAll();

    Stream<Singer> findByFirstName(String firstName);

    void saveAll(Iterable<Singer> singers);
}
