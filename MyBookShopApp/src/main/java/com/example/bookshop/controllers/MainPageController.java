package com.example.bookshop.controllers;

import com.example.bookshop.data.Book;
import com.example.bookshop.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class MainPageController {

    private final BookService _bookService;

    @Autowired
    public MainPageController(BookService aBookService) {
        _bookService = aBookService;
    }

    @ModelAttribute("activePage")
    public ActivePageType activePage() {
        return ActivePageType.MAIN;
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return  _bookService.getBooksData();
    }

    @ModelAttribute("recentBooks")
    public List<Book> recentBooks() {
        return _bookService.getRecentBooks();
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks() {
        List<Book> list = _bookService.getPupularBooks();
        return list;
    }

    @GetMapping({"/", "/index.html"})
    public String mainPage(Model aModel) {
//        aModel.addAttribute("recommendedBooks", _bookService.getBooksData());
//        aModel.addAttribute("recentBooks",  _bookService.getRecentBooks());
//        aModel.addAttribute("popularBooks", _bookService.getPupularBooks());
        return "index";
    }

}
