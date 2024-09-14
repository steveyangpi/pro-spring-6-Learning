package com.apress.prospring6.fifteen.controllers;

import com.apress.prospring6.fifteen.entities.Singer;
import com.apress.prospring6.fifteen.repos.SingerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "singer")
public class SingerController {
    final Logger LOGGER = LoggerFactory.getLogger(SingerController.class);

    private final SingerRepo singerRepo;

    public SingerController(SingerRepo singerRepo) {
        this.singerRepo = singerRepo;
    }

    @ResponseStatus(HttpStatus.OK)
    // @GetMapping(path={"/",""}
    @RequestMapping(path = {"/",""},method = RequestMethod.GET)
    public List<Singer> all(){
        return singerRepo.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    // @GetMapping(path={"/","{id}"}
    @RequestMapping(path = "/{id}",method = RequestMethod.GET)
    public Singer findSingerById(@PathVariable Long id){
        return singerRepo.findById(id).orElse(null);
    }

    @ResponseStatus(HttpStatus.CREATED)
    //@PostMapping(path="/")
    @RequestMapping(path = "/",method = RequestMethod.POST)
    public Singer create(@RequestBody Singer singer){
        LOGGER.info("Creating singer: " + singer);
        var saved = singerRepo.save(singer);
        LOGGER.info("Singer created successfully with info: " + saved);
        return singer;
    }

    @ResponseStatus(HttpStatus.OK)
    //@PutMapping(value="/{id}")
    @RequestMapping(path = "/{id}",method = RequestMethod.PUT)
    public void update(@RequestBody Singer singer,@PathVariable Long id){
        LOGGER.info("Updating singer: {}",singer);
        var fromDb = singerRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Singer does not exist!"));
        fromDb.setFirstName(singer.getFirstName());
        fromDb.setLastName(singer.getLastName());
        fromDb.setBirthDate(singer.getBirthDate());
        singerRepo.save(fromDb);
        LOGGER.info("Singer updated successfully with info: " + fromDb);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@DeleteMapping(value="{id}")
    @RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        LOGGER.info("Deleting singer with id: {}",id);
        singerRepo.deleteById(id);
        LOGGER.info("Singer deleted successfully");
    }
}
