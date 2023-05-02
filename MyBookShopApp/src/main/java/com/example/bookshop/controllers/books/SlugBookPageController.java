package com.example.bookshop.controllers.books;

import com.example.bookshop.controllers.AbstractPageController;
import com.example.bookshop.data.dto.BookDTO;
import com.example.bookshop.data.dto.DraftBookReviewDTO;
import com.example.bookshop.data.dto.SlugDTO;
import com.example.bookshop.data.entities.enums.BookBindingType;
import com.example.bookshop.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SlugBookPageController extends AbstractPageController {

    private final BookReviewService bookReviewService;

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final UserService userService;

    @Autowired
    public SlugBookPageController(BookReviewService aBookReviewService, BookService aBookService, AuthorService aAuthorService, GenreService aGenreService, UserService aUserService) {
        bookReviewService = aBookReviewService;
        bookService = aBookService;
        authorService = aAuthorService;
        genreService = aGenreService;
        userService = aUserService;
    }

    @GetMapping(value = "/books/{slugBook}")
    public String slugPage(@PathVariable(value = "slugBook") SlugDTO aSlug, Model aModel) {
        if (aSlug != null) {
            BookDTO book = bookService.getBookBySlug(aSlug.getName());
            if (book != null) {
                aModel.addAttribute("bookDTO", book);
                aModel.addAttribute("bookReviews", bookReviewService.getReviersByBookId(book.getId()));
                aModel.addAttribute("bookAuthors", authorService.findByBookId(book.getId()));
                aModel.addAttribute("bookGenres", genreService.findByBookId(book.getId()));
                return "books/slug";
            }
        }
        aModel.addAttribute("bookDTO", BookDTO.builder()
                .id(-1L)
                .build());
        aModel.addAttribute("bookReviews", List.of());
        aModel.addAttribute("bookAuthors", List.of());
        aModel.addAttribute("bookGenres", List.of());
        return "books/slug";
    }

    @ResponseBody
    @PostMapping(value = "/changeBookStatus")
    public boolean changeBookStatus(
            @RequestParam("booksIds[]") List<Long> aBookIds,
            @RequestParam("status") BookBindingType aStatus) {
        if ((!aBookIds.isEmpty()) && (aStatus != null)) {
            bookService.changeBookStatus(aBookIds.get(0), 1L, aStatus);
            return true;
        }
        return false;
    }

    @PostMapping(value = "/addBookReviewAction")
    public String saveReview(
            @RequestParam("bookid") Long aBookId,
            @ModelAttribute("action") String aAction,
            @ModelAttribute("review-text") String aText) {
        BookDTO book = bookService.FindById(aBookId);
        if (book != null) {
            if ("confirm".equals(aAction)) {
                bookReviewService.saveNewReview(DraftBookReviewDTO.builder()
                        .bookId(book.getId())
                        .userId(1L)
                        .text(aText)
                        .build());
            }
            return ("redirect:/books/" + book.getSlug());
        }
        return null;
    }

}
