package com.example.bookshop.controllers;

import com.example.bookshop.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/books", method = RequestMethod.GET)
public class BookPagesController extends AbstractPageController {

    private final BookService bookService;

    public BookPagesController(BookService aBookServive) {
        bookService = aBookServive;
    }

    @GetMapping(value = "/recent")
    public String recentPage(Model aModel) {
        aModel.addAttribute("recentBooks", bookService.getRecentBooks());
        return "books/recent";
    }

    @GetMapping(value = "/popular")
    public String popularPage(Model aModel) {
        aModel.addAttribute("popularBooks", bookService.getPupularBooks());
        return "books/popular";
    }

}
