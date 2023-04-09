package com.example.bookshop.controllers;

import com.example.bookshop.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookPagesController {

    private final BookService _bookService;

    public BookPagesController(BookService aBookServive) {
        _bookService = aBookServive;
    }

    @GetMapping({"books/recent", "books/recent.html"})
    public String recentPage(Model aModel) {
        aModel.addAttribute("activePage", ActivePageType.RECENT);
        aModel.addAttribute("recentBooks", _bookService.getRecentBooks());
        return "books/recent";
    }

    @GetMapping({"books/popular", "books/popular.html"})
    public String popularPage(Model aModel) {
        aModel.addAttribute("activePage", ActivePageType.POPULARS);
        aModel.addAttribute("popularBooks", _bookService.getPupularBooks());
        return "books/popular";
    }

}
