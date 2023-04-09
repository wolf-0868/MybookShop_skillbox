package com.example.bookshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignPagesController {

    @GetMapping({"/signin", "/signin.html"})
    public String signInPage() {
        return "signin";
    }

    @GetMapping({"/signup", "signup.html"})
    public String signUp() {
        return "signup";
    }

}
