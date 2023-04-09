package com.example.bookshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactsPageController {

    @GetMapping({"/contacts", "/contacts.html"})
    public String contactsPage() {
        return "contacts";
    }

}
