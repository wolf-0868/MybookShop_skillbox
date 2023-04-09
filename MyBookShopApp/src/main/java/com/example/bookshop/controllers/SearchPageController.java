package com.example.bookshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchPageController {

    @GetMapping({"/search", "/search/index.html"})
    public String searchPage() {
        return "search";
    }

}
