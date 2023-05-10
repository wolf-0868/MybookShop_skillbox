package com.example.bookshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/cart", method = RequestMethod.GET)
public class CartPageController {

    @GetMapping
    public String cartPage() {
        return "cart";
    }

}
