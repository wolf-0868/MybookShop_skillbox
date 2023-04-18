package com.example.bookshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/genres", method = RequestMethod.GET)
public class GenresPageController extends AbstractPageController {

    @GetMapping(value = {"", "/index"})
    public String indexPage() {
        return "genres/index";
    }

}
