package com.apress.prospring6.ten.boot.service;

import com.apress.prospring6.ten.boot.entities.Singer;
import com.apress.prospring6.ten.boot.repos.SingerRepository;

import java.util.stream.Stream;

public interface SingerService {

    Stream<Singer> findAll();

    Stream<Singer> findAllWithAlbums();

    Stream<Singer> findByFirstName(String firstName);

    Stream<Singer> findByFirstNameAndLastName(String firstName, String lastName);

    Singer updateFirstName(String firstName, Long id);

    Stream<SingerRepository.FullName> findByLastName(String lastName);
}
