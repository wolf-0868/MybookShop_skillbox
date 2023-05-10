package com.example.bookshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/contacts", method = RequestMethod.GET)
public class ContactsPageController {

    @GetMapping
    public String contactsPage() {
        return "contacts";
    }

}
