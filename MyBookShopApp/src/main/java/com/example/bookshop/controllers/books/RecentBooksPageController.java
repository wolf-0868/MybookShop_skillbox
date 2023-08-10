package com.example.bookshop.controllers.books;

import com.example.bookshop.data.dto.page.BooksPageDTO;
import com.example.bookshop.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class RecentBooksPageController {

    private final BookService bookService;

    @ModelAttribute
    protected void addAttributes(Model aModel) {
        aModel.addAttribute("recentBooks", bookService.getPageOfRecentBooks(0, 20));
    }

    @GetMapping(value = "/books/recent")
    public String recentPage(Model aModel) {
        return "books/recent";
    }

    @ResponseBody
    @GetMapping(value = "/books/recent", params = {"from", "to", "offset", "limit"})
    public BooksPageDTO getRecentBooksPage(
            @RequestParam("from") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate aFromDate,
            @RequestParam("to") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate aToDate,
            @RequestParam("offset") Integer aOffset,
            @RequestParam("limit") Integer aLimit) {
        return new BooksPageDTO(bookService.getPageBooksOfRange(aFromDate, aToDate, aOffset, aLimit));
    }

    @ResponseBody
    @GetMapping(value = "/books/recent", params = {"offset", "limit"})
    public BooksPageDTO getRecentBooksPage(
            @RequestParam("offset") Integer aOffset,
            @RequestParam("limit") Integer aLimit) {
        return new BooksPageDTO(bookService.getPageOfRecentBooks(aOffset, aLimit));
    }

}
