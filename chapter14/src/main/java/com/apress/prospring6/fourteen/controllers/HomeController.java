package com.apress.prospring6.fourteen.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(path = "home")
    public String home(Model model){
        model.addAttribute("message","Spring MVC ThymeleafExample!!");
        return "home";
    }
}