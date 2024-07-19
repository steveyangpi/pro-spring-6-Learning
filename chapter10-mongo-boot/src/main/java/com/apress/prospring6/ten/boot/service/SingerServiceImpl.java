package com.apress.prospring6.ten.boot.service;

import com.apress.prospring6.ten.boot.document.Singer;
import com.apress.prospring6.ten.boot.repos.SingerRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class SingerServiceImpl implements SingerService{
    private final SingerRepository singerRepository;

    public SingerServiceImpl(SingerRepository singerRepository) {
        this.singerRepository = singerRepository;
    }

    @Override
    public Stream<Singer> findAll() {
        return StreamSupport.stream(singerRepository.findAll().spliterator(),false);

    }

    @Override
    public Stream<Singer> findByFirstName(String firstName) {
        return StreamSupport.stream(singerRepository.findByFirstName(firstName).spliterator(),false);
    }

    @Override
    public void saveAll(Iterable<Singer> singers) {
        singerRepository.saveAll(singers);
    }
}
