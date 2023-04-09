package com.example.bookshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class GenresPageController {

    @ModelAttribute("activePage")
    public ActivePageType activePage() {
        return ActivePageType.GENRES;
    }

    @GetMapping({"/genres","genres/index.html"})
    public String indexPage() {
        return "genres/index";
    }

}
