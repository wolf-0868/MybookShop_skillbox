package com.example.bookshop.controllers.books;

import com.example.bookshop.data.dto.BookDTO;
import com.example.bookshop.data.dto.SlugDTO;
import com.example.bookshop.data.dto.drafts.DraftBookReviewDTO;
import com.example.bookshop.data.dto.page.BookStatusesPageDTO;
import com.example.bookshop.data.dto.page.RatingBookPage;
import com.example.bookshop.data.entities.enums.BookBindingType;
import com.example.bookshop.data.payloads.ChangeBookStatusPayload;
import com.example.bookshop.data.payloads.RateBookPayload;
import com.example.bookshop.data.payloads.RateBookReviewPayload;
import com.example.bookshop.exceptions.BookshopException;
import com.example.bookshop.exceptions.DataNotFoundException;
import com.example.bookshop.exceptions.UserNotFountException;
import com.example.bookshop.security.BookshopUserRegistrar;
import com.example.bookshop.security.ConfirmationResponse;
import com.example.bookshop.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Log
@Controller
@RequiredArgsConstructor
public class SlugBookPageController {

    private final BookReviewService bookReviewService;
    private final BookshopUserRegistrar bookshopUserRegistrar;
    private final Book2UserService book2UserService;
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final JournalService journalService;
    private final RatingBookService ratingBookService;

    @GetMapping(value = "/books/{slugBook}")
    public String slugPage(@PathVariable(value = "slugBook") SlugDTO aSlug, Model aModel) throws DataNotFoundException {
        BookDTO book = bookService.findBookBySlug(aSlug.getName());
        aModel.addAttribute("bookDTO", book);
        aModel.addAttribute("bookReviews", bookReviewService.findReviewsByBookId(book.getId()));
        aModel.addAttribute("bookAuthors", authorService.findByBookId(book.getId()));
        aModel.addAttribute("bookGenres", genreService.findByBookId(book.getId()));
        aModel.addAttribute("bookRatings", new RatingBookPage(ratingBookService.getGroupingRating(book.getId())));
        try {
            Long userId = bookshopUserRegistrar.getCurrentIdUser();
            aModel.addAttribute("ratingForUser", ratingBookService.getRatingBookForUser(userId, book.getId()));
            aModel.addAttribute("bookStatuses", new BookStatusesPageDTO(book2UserService.findBindingTypesByBookForUser(book.getId(), userId)));
            journalService.logReviewBook(book.getId(), userId);
        } catch (UserNotFountException e) {
            log.fine("Пользователь не авторизирован");
        }
        return "books/slug";
    }

    @ResponseBody
    @PostMapping(value = "/changeBookStatus")
    public ConfirmationResponse handleChangeBookStatus(@RequestBody ChangeBookStatusPayload aPayload) throws BookshopException {
        ConfirmationResponse response = new ConfirmationResponse(false);
        if (aPayload.getBooksIds().isEmpty() || (aPayload.getStatus() == null)) {
            return response;
        }
        if ((aPayload.getBooksIds().size() == 1) && (aPayload.getStatus() != BookBindingType.PAID)) {
            book2UserService.changeBookBindingType(aPayload.getBooksIds()
                    .iterator()
                    .next(), bookshopUserRegistrar.getCurrentIdUser(), aPayload.getStatus());
        } else {
            switch (aPayload.getStatus()) {
                case PAID:
                    book2UserService.buyBooks(aPayload.getBooksIds(), bookshopUserRegistrar.getCurrentIdUser());
                    break;
                case CART:
                    book2UserService.addToCart(aPayload.getBooksIds(), bookshopUserRegistrar.getCurrentIdUser());
                    break;
                default:
                    throw new UserNotFountException("Групповое изменение статуса '%s' не реализована", aPayload.getStatus());
            }
        }
        response.setResult(true);
        return response;
    }

    @PostMapping(value = "/addBookReviewAction")
    public String handleSaveReview(
            @RequestParam("bookid") Long aBookId,
            @ModelAttribute("action") String aAction,
            @ModelAttribute("review-text") String aText) throws UserNotFountException, DataNotFoundException {
        BookDTO book = bookService.findById(aBookId);
        if ("confirm".equals(aAction)) {
            bookReviewService.saveNewReview(DraftBookReviewDTO.builder()
                    .bookId(book.getId())
                    .userId(bookshopUserRegistrar.getCurrentIdUser())
                    .text(aText)
                    .build());
        }
        return ("redirect:/books/" + book.getSlug());
    }

    @ResponseBody
    @PostMapping(value = "/rateBook")
    public ConfirmationResponse handleRateBook(@RequestBody RateBookPayload aPayload) throws UserNotFountException {
        ConfirmationResponse response = new ConfirmationResponse(false);
        ratingBookService.updateRatingBook(aPayload.getBookId(), bookshopUserRegistrar.getCurrentIdUser(), aPayload.getValue());
        response.setResult(true);
        return response;
    }

    @ResponseBody
    @PostMapping(value = "/rateBookReview")
    public ConfirmationResponse handleRateBookReview(@RequestBody RateBookReviewPayload aPayload) throws UserNotFountException {
        ConfirmationResponse response = new ConfirmationResponse(false);
        ratingBookService.updateRatingBookReview(aPayload.getReviewId(), bookshopUserRegistrar.getCurrentIdUser(), aPayload.getValue());
        response.setResult(true);
        return response;
    }

}
