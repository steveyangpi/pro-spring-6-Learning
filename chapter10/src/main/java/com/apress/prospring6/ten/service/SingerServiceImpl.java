package com.apress.prospring6.ten.service;

import com.apress.prospring6.ten.entities.Singer;
import com.apress.prospring6.ten.repos.SingerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class SingerServiceImpl implements SingerService {

    private final SingerRepository singerRepository;

    public SingerServiceImpl(SingerRepository singerRepository) {
        this.singerRepository = singerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Stream<Singer> findAll() {
        return StreamSupport.stream(singerRepository.findAll().spliterator(), false);
    }

    @Override
    @Transactional(readOnly = true)
    public Stream<Singer> findByFirstName(String firstName) {
        return StreamSupport.stream(singerRepository.findByFirstName(firstName).spliterator(), false);
    }

    @Override
    @Transactional(readOnly = true)
    public Stream<Singer> findByFirstNameAndLastName(String firstName, String lastName) {
        return StreamSupport.stream(singerRepository.findByFirstNameAndLastName(firstName, lastName).spliterator(), false);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,label = "modifying")
    @Override
    public Singer updateFirstName(String firstName, Long id) {
       singerRepository.findById(id).ifPresent(s -> singerRepository.setFirstNameFor(firstName,id));
       return singerRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public Stream<SingerRepository.FullName> findByLastName(String lastName) {
        return StreamSupport.stream(singerRepository.findByLastName(lastName).spliterator(), false);
    }
}
