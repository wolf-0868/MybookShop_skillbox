package com.example.bookshop.controllers;

import com.example.bookshop.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/", method = RequestMethod.GET)
public class MainPageController extends AbstractPageController {

    private final BookService bookService;

    @Autowired
    public MainPageController(BookService aBookService) {
        bookService = aBookService;
    }

    @Override
    public void addAttributes(Model aModel) {
        super.addAttributes(aModel);
        aModel.addAttribute("recommendedBooks", bookService.getAllBooks());
        aModel.addAttribute("recentBooks", bookService.getRecentBooks());
        aModel.addAttribute("popularBooks", bookService.getPupularBooks());
    }

    @GetMapping(value = {"", "/index"})
    public String mainPage(Model aModel) {
        return "index";
    }

}
