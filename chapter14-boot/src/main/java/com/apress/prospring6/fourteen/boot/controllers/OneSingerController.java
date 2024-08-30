package com.apress.prospring6.fourteen.boot.controllers;

import com.apress.prospring6.fourteen.boot.entities.Singer;
import com.apress.prospring6.fourteen.boot.problem.NotFoundException;
import com.apress.prospring6.fourteen.boot.services.SingerService;
import jakarta.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

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
    public String showSingerData(@PathVariable("id") Long id,Model uiModel){
        Singer singer = singerService.findById(id);
        uiModel.addAttribute("singer",singer);

        return "singers/show";
    }

    //@RequestMapping(path = "/edit", method = RequestMethod.GET)
    @GetMapping(path = "/edit")
    public String showEditorForm(@PathVariable("id") Long id,Model uiModel){
        Singer singer = singerService.findById(id);

        uiModel.addAttribute("singer",singer);
        return "singers/edit";
    }

    @GetMapping(path = "/upload")
    public String showPhotoUploadForm(@PathVariable("id") Long id,Model uiModel){
        Singer singer = singerService.findById(id);

        uiModel.addAttribute("singer",singer);
        return "singers/upload";
    }

    @PutMapping
    public String updateSingerInfo(@Valid Singer singer, BindingResult bindingResult, Model uiModel,
                                   Locale locale){
        if(bindingResult.hasErrors()){
            uiModel.addAttribute("message",messageSource.getMessage("singer.save.fail",new Object[]{},locale));
            uiModel.addAttribute("singer",singer);
            return "singers/edit";
        }else{
            uiModel.asMap().clear();

            var fromDb = singerService.findById(singer.getId());

            fromDb.setFirstName(singer.getFirstName());
            fromDb.setLastName(singer.getLastName());
            fromDb.setBirthDate(singer.getBirthDate());

            singerService.save(fromDb);
            return "redirect:/singer/" + singer.getId();
        }
    }

    @RequestMapping(value = "/photot",method = RequestMethod.GET)
    @ResponseBody
    public byte[] downloadPhoto(@PathVariable("id") Long id){
        Singer singer = singerService.findById(id);

        if(singer.getPhoto()!=null){
            LOGGER.info("Downloading photo for id: {} with size: {}",singer.getId(),singer.getPhoto().length);
            return singer.getPhoto();
        }
        return null;
    }

    @PostMapping(path = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String handlerFileUpload(@RequestParam(value = "file",required = false) MultipartFile file,@PathVariable("id") Long id)throws NotFoundException,IOException {
        var fromDb = singerService.findById(id);

        if(!file.isEmpty()){
            setPhoto(fromDb,file);
            singerService.save(fromDb);
        }

        return "redirect:/singer/" + id;
    }

    //@RequestMapping(method = RequestMethod.DELETE)
    @DeleteMapping
    public String deleteSinger(@PathVariable("id") Long id)throws NotFoundException{
        singerService.findById(id);
        singerService.delete(id);
        return "redirect:/singer/list";
    }

    static void setPhoto(Singer singer, MultipartFile file)throws IOException{
        InputStream inputStream = file.getInputStream();
        var fileContent= IOUtils.toByteArray(inputStream);
        singer.setPhoto(fileContent);
    }
}
