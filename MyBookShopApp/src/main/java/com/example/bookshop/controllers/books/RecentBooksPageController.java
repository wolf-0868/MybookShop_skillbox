package com.example.bookshop.controllers.books;

import com.example.bookshop.data.dto.BooksPageDTO;
import com.example.bookshop.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping(value = "/books/recent", method = {RequestMethod.GET, RequestMethod.POST})
public class RecentBooksPageController {

    private final BookService bookService;

    @Autowired
    public RecentBooksPageController(BookService aBookService) {
        bookService = aBookService;
    }

    @ModelAttribute
    protected void addAttributes(Model aModel) {
        aModel.addAttribute("recentBooks", bookService.getPageOfRecentBooks(0, 20));
    }

    @GetMapping
    public String recentPage(Model aModel) {
        return "books/recent";
    }

    @ResponseBody
    @PostMapping(params = {"from", "to", "offset", "limit"})
    public BooksPageDTO getRecentBooksPage(
            @RequestParam("from") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate aFromDate,
            @RequestParam("to") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate aToDate,
            @RequestParam("offset") Integer aOffset,
            @RequestParam("limit") Integer aLimit) {
        return new BooksPageDTO(bookService.getPageOfBooks(aFromDate, aToDate, aOffset, aLimit));
    }

    @ResponseBody
    @PostMapping(params = {"offset", "limit"})
    public BooksPageDTO getRecentBooksPage(
            @RequestParam("offset") Integer aOffset,
            @RequestParam("limit") Integer aLimit) {
        return new BooksPageDTO(bookService.getPageOfRecentBooks(aOffset, aLimit));
    }

}
