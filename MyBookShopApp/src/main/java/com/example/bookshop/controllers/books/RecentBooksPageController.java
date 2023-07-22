package com.example.bookshop.controllers.books;

import com.example.bookshop.data.dto.page.BooksPageDTO;
import com.example.bookshop.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping(value = "/books/recent", method = {RequestMethod.GET, RequestMethod.POST})
@RequiredArgsConstructor
public class RecentBooksPageController {

    private final BookService bookService;

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
        return new BooksPageDTO(bookService.getPageBooksOfRange(aFromDate, aToDate, aOffset, aLimit));
    }

    @ResponseBody
    @PostMapping(params = {"offset", "limit"})
    public BooksPageDTO getRecentBooksPage(
            @RequestParam("offset") Integer aOffset,
            @RequestParam("limit") Integer aLimit) {
        return new BooksPageDTO(bookService.getPageOfRecentBooks(aOffset, aLimit));
    }

}
