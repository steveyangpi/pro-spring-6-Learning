package com.apress.prospring6.fifteen.boot.services;

import com.apress.prospring6.fifteen.boot.entities.Singer;

import java.util.List;

public interface SingerService {
    List<Singer> findAll();
    Singer findById(Long id);

    Singer save(Singer singer);
    Singer update(Long id,Singer singer);

    void delete(Long id);
}
