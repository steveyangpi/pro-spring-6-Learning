package com.apress.prospring6.fifteen.controllers;

import com.apress.prospring6.fifteen.entities.Singer;
import com.apress.prospring6.fifteen.services.SingerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "singer3")
public class Singer3Controller {

    final Logger LOGGER = LoggerFactory.getLogger(Singer3Controller.class);

    private final SingerService singerService;

    public Singer3Controller(SingerService singerService) {
        this.singerService = singerService;
    }

    @GetMapping(path ={"/",""})
    public List<Singer> all(){return singerService.findAll();}

    @GetMapping(path = "/{id}")
    public Singer findSingerById(@PathVariable Long id){return singerService.findById(id);}

    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public Singer create(@RequestBody @Valid Singer singer){
        LOGGER.info("Creating singer: " + singer);
        return singerService.save(singer);
    }

    @PutMapping(value = "/{id}")
    public void update(@RequestBody @Valid Singer singer,@PathVariable Long id){
        LOGGER.info("Updating singer: " + singer);

        singerService.update(id, singer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value="/{id}")
    public void delete(@PathVariable Long id){
        LOGGER.info("Deleting singer with id: {}",id);
        singerService.delete(id);
    }
}
