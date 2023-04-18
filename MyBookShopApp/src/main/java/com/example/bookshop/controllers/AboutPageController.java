package com.example.bookshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/about", method = RequestMethod.GET)
public class AboutPageController extends AbstractPageController {

    @GetMapping
    public String aboutPage() {
        return "about";
    }

}
