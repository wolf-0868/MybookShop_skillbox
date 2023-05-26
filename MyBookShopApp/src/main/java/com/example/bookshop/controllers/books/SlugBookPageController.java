package com.example.bookshop.controllers.books;

import com.example.bookshop.data.dto.BookDTO;
import com.example.bookshop.data.dto.SlugDTO;
import com.example.bookshop.data.dto.drafts.DraftBookReviewDTO;
import com.example.bookshop.data.payloads.ChangeBookStatusPayload;
import com.example.bookshop.exceptions.DataNotFoundException;
import com.example.bookshop.exceptions.UserNotFountException;
import com.example.bookshop.security.BookshopUserRegistrar;
import com.example.bookshop.security.ConfirmationResponse;
import com.example.bookshop.services.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Log
@Controller
public class SlugBookPageController {

    private final BookReviewService bookReviewService;
    private final BookshopUserRegistrar bookshopUserRegistrar;
    private final Book2UserService book2UserService;
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Autowired
    public SlugBookPageController(BookReviewService aBookReviewService, BookshopUserRegistrar aBookshopUserRegistrar, Book2UserService aBook2UserService, BookService aBookService, AuthorService aAuthorService, GenreService aGenreService) {
        bookReviewService = aBookReviewService;
        bookshopUserRegistrar = aBookshopUserRegistrar;
        book2UserService = aBook2UserService;
        bookService = aBookService;
        authorService = aAuthorService;
        genreService = aGenreService;
    }

    @GetMapping(value = "/books/{slugBook}")
    public String slugPage(@PathVariable(value = "slugBook") SlugDTO aSlug, Model aModel) throws UserNotFountException, DataNotFoundException {
        BookDTO book = bookService.getBookBySlug(aSlug.getName());
        aModel.addAttribute("bookDTO", book);
        aModel.addAttribute("bookReviews", bookReviewService.getReviewsByBookId(book.getId()));
        aModel.addAttribute("bookAuthors", authorService.findByBookId(book.getId()));
        aModel.addAttribute("bookGenres", genreService.findByBookId(book.getId()));
        try {
            aModel.addAttribute("bookStatuses", book2UserService.getAllBindingTypesByBook(book.getId(), bookshopUserRegistrar.getCurrentIdUser()));
        } catch (UserNotFountException e) {
            log.fine("Пользователь не авторизирован");
        }
        return "books/slug";
    }

    @ResponseBody
    @PostMapping(value = "/changeBookStatus")
    public ConfirmationResponse changeBookStatus(@RequestBody ChangeBookStatusPayload aPayload) throws UserNotFountException {
        ConfirmationResponse response = new ConfirmationResponse();
        if (!aPayload.getBooksIds()
                .isEmpty() && (aPayload.getStatus() != null)) {
            for (Long id : aPayload.getBooksIds()) {
                book2UserService.changeBookindingType(id, bookshopUserRegistrar.getCurrentIdUser(), aPayload.getStatus());
            }
            response.setResult("true");
        } else {
            response.setResult("false");
        }
        return response;
    }

    @PostMapping(value = "/addBookReviewAction")
    public String saveReview(@RequestParam("bookid") Long aBookId, @ModelAttribute("action") String aAction, @ModelAttribute("review-text") String aText) throws UserNotFountException, DataNotFoundException {
        BookDTO book = bookService.FindById(aBookId);
        if ("confirm".equals(aAction)) {
            bookReviewService.saveNewReview(DraftBookReviewDTO.builder()
                    .bookId(book.getId())
                    .userId(bookshopUserRegistrar.getCurrentIdUser())
                    .text(aText)
                    .build());
        }
        return ("redirect:/books/" + book.getSlug());
    }

}
