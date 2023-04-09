package com.example.bookshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartPageController {

    @GetMapping({"/cart", "/cart.html"})
    public String cartPage() {
        return "cart";
    }

}
