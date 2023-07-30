package com.example.bookshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelpPageController {

    @GetMapping(value = "/faq")
    public String helpPage() {
        return "faq";
    }

}
