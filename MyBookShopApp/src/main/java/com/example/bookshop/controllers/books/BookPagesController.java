package com.example.bookshop.controllers.books;

import com.example.bookshop.data.dto.page.BooksPageDTO;
import com.example.bookshop.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/books", method = {RequestMethod.GET, RequestMethod.POST})
@RequiredArgsConstructor
public class BookPagesController {

    private final BookService bookService;

    @ResponseBody
    @PostMapping(value = "/recommended", params = {"offset", "limit"})
    public BooksPageDTO getRecommendedBooksPage(
            @RequestParam("offset") Integer aOffset,
            @RequestParam("limit") Integer aLimit) {
        return new BooksPageDTO(bookService.getRecommendedBooks(aOffset, aLimit));
    }

}
