package com.apress.prospring6.fourteen.controllers;

import com.apress.prospring6.fourteen.entities.Singer;
import com.apress.prospring6.fourteen.services.SingerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/singer/{id}")
public class OneSingerController {

    private final Logger LOGGER = LoggerFactory.getLogger(OneSingerController.class);
    private final SingerService singerService;
    private final MessageSource messageSource;

    public OneSingerController(SingerService singerService, MessageSource messageSource) {
        this.singerService = singerService;
        this.messageSource = messageSource;
    }

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public String showSingerDate(@PathVariable("id") Long id, Model uiModel){
        Singer singer = singerService.findById(id);
        uiModel.addAttribute("singer",singer);

        return "singers/show";
    }

    @GetMapping("photo")
    //@RequestMapping(value = "/photo",method = RequestMethod.GET)
    @ResponseBody
    public byte[] downloadPhoto(@PathVariable("id") Long id){
        Singer singer = singerService.findById(id);

        if(singer.getPhoto()!=null){
            LOGGER.info("Downloading photo for id: {} with size: {}",singer.getId(),singer.getPhoto().length);
            return singer.getPhoto();
        }
        return null;
    }

    //@RequestMapping(method = RequestMethod.POST)
    @DeleteMapping
    public String deleteSinger(@PathVariable("id") Long id){
        singerService.findById(id);
        singerService.delete(id);
        return "redirect:/singer/list";
    }
}
