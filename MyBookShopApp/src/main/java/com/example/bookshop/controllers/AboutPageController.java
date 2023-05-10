package com.example.bookshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutPageController {

    @GetMapping(value = "/about")
    public String aboutPage() {
        return "about";
    }

}
