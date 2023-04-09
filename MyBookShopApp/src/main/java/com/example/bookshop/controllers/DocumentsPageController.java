package com.example.bookshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DocumentsPageController {

    @GetMapping({"/documents", "/documents/index.html"})
    public String documentsPage() {
        return "documents/index";
    }

}
