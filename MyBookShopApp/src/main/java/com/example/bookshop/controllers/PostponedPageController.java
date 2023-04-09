package com.example.bookshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostponedPageController {

    @GetMapping({"/postponed", "/postponed/index.html"})
    public String postponedPage() {
        return "postponed";
    }

}
