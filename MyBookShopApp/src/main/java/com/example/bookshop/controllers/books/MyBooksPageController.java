package com.example.bookshop.controllers.books;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/my", method = RequestMethod.GET)
public class MyBooksPageController {

    @GetMapping
    public String getMyPage() {
        return "my";
    }

}
