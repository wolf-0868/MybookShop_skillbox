package com.example.bookshop.controllers.books;

import com.example.bookshop.data.dto.page.BooksPageDTO;
import com.example.bookshop.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PopularBooksPageController {

    private final BookService bookService;

    @ModelAttribute
    protected void addAttributes(Model aModel) {
        aModel.addAttribute("popularBooks", bookService.getPageOfPopularBooks(0, 20));
    }

    @GetMapping(value = "/books/popular")
    public String popularPage() {
        return "books/popular";
    }

    @ResponseBody
    @PostMapping(value = "/books/popular", params = {"offset", "limit"})
    public BooksPageDTO getPopularBooksPage(
            @RequestParam("offset") Integer aOffset,
            @RequestParam("limit") Integer aLimit) {
        return new BooksPageDTO(bookService.getPageOfPopularBooks(aOffset, aLimit));
    }

}
