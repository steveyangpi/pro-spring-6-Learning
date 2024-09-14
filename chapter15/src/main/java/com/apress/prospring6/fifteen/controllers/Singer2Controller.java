package com.apress.prospring6.fifteen.controllers;

import com.apress.prospring6.fifteen.entities.Singer;
import com.apress.prospring6.fifteen.repos.SingerRepo;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "singer2")
public class Singer2Controller {

    final Logger LOGGER = LoggerFactory.getLogger(Singer2Controller.class);

    private final SingerRepo singerRepo;

    public Singer2Controller(SingerRepo singerRepo) {
        this.singerRepo = singerRepo;
    }

    @GetMapping(path = {"/", ""})
    public ResponseEntity<List<Singer>> all() {
        var singers = singerRepo.findAll();
        if (singers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(singerRepo.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Singer> findSingerById(@PathVariable Long id) {
        Optional<Singer> fromDb = singerRepo.findById(id);
        return fromDb
                .map(s -> ResponseEntity.ok().body(s))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/")
    public ResponseEntity<Singer> create(@RequestBody @Valid Singer singer) {
        LOGGER.info("Creating singer: {}", singer);

        try {
            var saved = singerRepo.save(singer);
            LOGGER.info("Singer created successfully with info: {}", saved);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException dive) {
            LOGGER.debug("Could not create singer.", dive);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> update(@RequestBody @Valid Singer singer, @PathVariable Long id) {
        LOGGER.info("Updating singer: {}", singer);
        Optional<Singer> fromDb = singerRepo.findById(id);

        return fromDb
                .map(s -> {
                    s.setFirstName(singer.getFirstName());
                    s.setLastName(singer.getLastName());
                    s.setBirthDate(singer.getBirthDate());
                    try {
                        singerRepo.save(s);
                        return ResponseEntity.ok().build();
                    } catch (DataIntegrityViolationException dive) {
                        LOGGER.debug("Could not update singer.", dive);
                        return ResponseEntity.badRequest().build();
                    }
                })
                .orElseGet(() ->  ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        LOGGER.info("Deleting singer with id: {}",id);

        Optional<Singer> fromDb = singerRepo.findById(id);
        return fromDb
                .map(s -> {
                    singerRepo.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
