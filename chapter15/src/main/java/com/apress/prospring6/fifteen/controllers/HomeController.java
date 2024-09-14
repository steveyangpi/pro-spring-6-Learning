package com.apress.prospring6.fifteen.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HomeController {

    @GetMapping(path = "home",produces = MediaType.TEXT_HTML_VALUE)
    public String home() {return "{\"message\",\"Spring MVC REST!!\"}"; }
}
