package com.example.bookshop.controllers;

import com.example.bookshop.data.dto.BooksPageDTO;
import com.example.bookshop.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/books", method = RequestMethod.GET)
public class BookPagesController extends AbstractPageController {

    private final BookService bookService;

    @Autowired
    public BookPagesController(BookService aBookService) {
        bookService = aBookService;
    }

    @ResponseBody
    @PostMapping(value = "/recommended", params = {"offset", "limit"})
    public BooksPageDTO getRecommendedBooksPage(
            @RequestParam("offset") Integer aOffset,
            @RequestParam("limit") Integer aLimit) {
        return new BooksPageDTO(bookService.getRecommendedBooks(aOffset, aLimit));
    }

}
