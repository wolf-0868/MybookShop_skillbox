package com.example.bookshop.controllers;

import com.example.bookshop.data.BookEntity;
import com.example.bookshop.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;

    @Autowired
    public MainPageController(BookService aBookService) {
        bookService = aBookService;
    }

    @ModelAttribute("activePage")
    public ActivePageType activePage() {
        return ActivePageType.MAIN;
    }

    @ModelAttribute("recommendedBooks")
    public List<BookEntity> recommendedBooks() {
        List<BookEntity> allBooks = bookService.getAllBooks();
        return allBooks;
    }

    @ModelAttribute("recentBooks")
    public List<BookEntity> recentBooks() {
        return bookService.getRecentBooks();
    }

    @ModelAttribute("popularBooks")
    public List<BookEntity> popularBooks() {
        List<BookEntity> list = bookService.getPupularBooks();
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
