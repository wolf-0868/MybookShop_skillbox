package com.example.bookshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignPagesController extends AbstractPageController {

    @GetMapping(value = "/signin")
    public String signInPage() {
        return "signin";
    }

    @GetMapping(value = "/signup")
    public String signUp() {
        return "signup";
    }

}
