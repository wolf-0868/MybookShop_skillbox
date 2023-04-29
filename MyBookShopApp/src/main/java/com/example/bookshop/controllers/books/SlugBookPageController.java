package com.example.bookshop.controllers.books;

import com.example.bookshop.controllers.AbstractPageController;
import com.example.bookshop.controllers.data.dto.BookDTO;
import com.example.bookshop.controllers.data.dto.SlugDTO;
import com.example.bookshop.controllers.data.entities.enums.BookBindingType;
import com.example.bookshop.services.BookReviewService;
import com.example.bookshop.services.BookService;
import com.example.bookshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SlugBookPageController extends AbstractPageController {

    private final BookService bookService;

    private final BookReviewService bookReviewService;

    private final UserService userService;

    @Autowired
    public SlugBookPageController(BookService aBookService, BookReviewService aBookReviewService, UserService aUserService) {
        bookService = aBookService;
        bookReviewService = aBookReviewService;
        userService = aUserService;
    }

    @GetMapping(value = "/books/{slugBook}")
    public String slugPage(@PathVariable(value = "slugBook") SlugDTO aSlug, Model aModel) {
        if (aSlug != null) {
            BookDTO book = bookService.getBookBySlug(aSlug.getName());
            if (book != null) {
                aModel.addAttribute("bookDTO", book);
                aModel.addAttribute("bookReviews", bookReviewService.getReviersByBookId(book.getId()));
                return "books/slug";
            }
        }
        aModel.addAttribute("bookDTO", BookDTO.builder()
                .id(-1L)
                .build());
        aModel.addAttribute("bookReviews", List.of());
        return "books/slug";
    }

    @ResponseBody
    @PostMapping(value = "/changeBookStatus")
    public void changeBookStatus(
            @RequestParam("booksIds[]") List<Long> aBookIds,
            @RequestParam("status") BookBindingType aStatus) {
        if ((!aBookIds.isEmpty()) && (aStatus != null)) {
            bookService.changeBookStatus(aBookIds.get(0), 1L, aStatus);
        }
    }

}

