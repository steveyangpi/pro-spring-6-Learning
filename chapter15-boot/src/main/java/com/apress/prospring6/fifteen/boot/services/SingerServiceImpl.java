package com.apress.prospring6.fifteen.boot.services;

import com.apress.prospring6.fifteen.boot.entities.Singer;
import com.apress.prospring6.fifteen.boot.problem.NotFoundException;
import com.apress.prospring6.fifteen.boot.repos.SingerRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("singerService")
public class SingerServiceImpl implements SingerService{

    private final SingerRepo singerRepo;

    public SingerServiceImpl(SingerRepo singerRepo) {this.singerRepo = singerRepo;}

    @Override
    public List<Singer> findAll() {
        var singers = singerRepo.findAll();
        if(singers.isEmpty()) {
            throw new NotFoundException(Singer.class);
        }
        return singers;
    }

    @Override
    public Singer findById(Long id) {
        return singerRepo.findById(id).orElseThrow(() -> new NotFoundException(Singer.class,id));
    }

    @Override
    public Singer save(Singer singer) {
        return singerRepo.save(singer);
    }

    @Override
    public Singer update(Long id, Singer singer) {
        return singerRepo.findById(id)
                .map(s ->{
                    s.setFirstName(singer.getFirstName());
                    s.setLastName(singer.getLastName());
                    s.setBirthDate(singer.getBirthDate());
                    return singerRepo.save(s);
                })
                .orElseThrow(() -> new NotFoundException(Singer.class,id));
    }

    @Override
    public void delete(Long id) {
        singerRepo.findById(id).orElseThrow(() -> new NotFoundException(Singer.class,id));
        singerRepo.deleteById(id);
    }
}
