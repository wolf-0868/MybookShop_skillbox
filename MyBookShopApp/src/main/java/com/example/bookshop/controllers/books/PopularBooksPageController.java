package com.example.bookshop.controllers.books;

import com.example.bookshop.controllers.AboutPageController;
import com.example.bookshop.controllers.data.dto.BooksPageDTO;
import com.example.bookshop.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/books/popular", method = {RequestMethod.GET, RequestMethod.POST})
public class PopularBooksPageController extends AboutPageController {

    private final BookService bookService;

    @Autowired
    public PopularBooksPageController(BookService aBookService) {
        bookService = aBookService;
    }

    @Override
    protected void addAttributes(Model aModel) {
        super.addAttributes(aModel);
        aModel.addAttribute("popularBooks", bookService.getPageOfPopularBooks(0, 20));
    }

    @GetMapping
    public String popularPage() {
        return "books/popular";
    }

    @ResponseBody
    @PostMapping(params = {"offset", "limit"})
    public BooksPageDTO getPopularBooksPage(
            @RequestParam("offset") Integer aOffset,
            @RequestParam("limit") Integer aLimit) {
        return new BooksPageDTO(bookService.getPageOfPopularBooks(aOffset, aLimit));
    }

}
