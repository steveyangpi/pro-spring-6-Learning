package com.apress.prospring6.ten.service;

import com.apress.prospring6.ten.entities.Singer;
import com.apress.prospring6.ten.repos.SingerRepository;

import java.util.stream.Stream;

public interface SingerService {

    Stream<Singer> findAll();

    Stream<Singer> findByFirstName(String firstName);

    Stream<Singer> findByFirstNameAndLastName(String firstName,String lastName);

    Singer updateFirstName(String firstName,Long id);

    Stream<SingerRepository.FullName> findByLastName(String lastName);
}
