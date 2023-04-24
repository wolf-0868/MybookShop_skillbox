package com.example.bookshop.controllers;

import com.example.bookshop.data.dto.AuthorDTO;
import com.example.bookshop.data.dto.BooksPageDTO;
import com.example.bookshop.data.dto.SlugDTO;
import com.example.bookshop.services.AuthorService;
import com.example.bookshop.services.BookService;
import com.example.bookshop.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping(value = "/books", method = RequestMethod.GET)
public class BookPagesController extends AbstractPageController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @Autowired
    public BookPagesController(BookService aBookService, AuthorService aAuthorService, GenreService aGenreService) {
        bookService = aBookService;
        authorService = aAuthorService;
        genreService = aGenreService;
    }

    @Override
    protected void addAttributes(Model aModel) {
        super.addAttributes(aModel);
        aModel.addAttribute("recentBooks", bookService.getPageOfRecentBooks(0, 20));
        aModel.addAttribute("popularBooks", bookService.getPageOfPopularBooks(0, 20));
        aModel.addAttribute("authorBooks", new ArrayList<>());
        aModel.addAttribute("authorDTO", AuthorDTO.builder()
                .id(-1L)
                .build());
    }

    @GetMapping(value = "/recent")
    public String recentPage(Model aModel) {
        return "books/recent";
    }

    @GetMapping(value = "/popular")
    public String popularPage(Model aModel) {
        return "books/popular";
    }

    @GetMapping(value = "/author/{authorSlug}")
    public String booksByAuthorPage(@PathVariable(value = "authorSlug", required = false) SlugDTO aSlug, Model aModel) {
        if (aSlug != null) {
            AuthorDTO author = authorService.findBySlug(aSlug.getName());
            if (author != null) {
                aModel.addAttribute("authorDTO", author);
                aModel.addAttribute("authorBooks", bookService.getBooksByAuthorId(author.getId(), 0, 20));
            }
        }
        return "books/author";
    }

    @ResponseBody
    @PostMapping(value = "/recommended", params = {"offset", "limit"})
    public BooksPageDTO getRecommendedBooksPage(@RequestParam("offset") Integer aOffset, @RequestParam("limit") Integer aLimit) {
        return new BooksPageDTO(bookService.getPageOfBooks(aOffset, aLimit));
    }

    @ResponseBody
    @PostMapping(value = "/recent", params = {"from", "to", "offset", "limit"})
    public BooksPageDTO getPopularBooksPage(@RequestParam("from") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate aFromDate, @RequestParam("to") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate aToDate, @RequestParam("offset") Integer aOffset, @RequestParam("limit") Integer aLimit) {
        return new BooksPageDTO(bookService.getPageOfBooks(aFromDate, aToDate, aOffset, aLimit));
    }

    @ResponseBody
    @PostMapping(value = "/recent", params = {"offset", "limit"})
    public BooksPageDTO getRecentBooksPage(@RequestParam("offset") Integer aOffset, @RequestParam("limit") Integer aLimit) {
        return new BooksPageDTO(bookService.getPageOfRecentBooks(aOffset, aLimit));
    }

    @ResponseBody
    @PostMapping(value = "/popular", params = {"offset", "limit"})
    public BooksPageDTO getPopularBooksPage(@RequestParam("offset") Integer aOffset, @RequestParam("limit") Integer aLimit) {
        return new BooksPageDTO(bookService.getPageOfPopularBooks(aOffset, aLimit));
    }

    @ResponseBody
    @PostMapping(value = "/genre/{genreId}", params = {"offset", "limit"})
    public BooksPageDTO getBooksByGenrePage(@PathVariable(value = "genreId", required = false) Long aGenreId, @RequestParam("offset") Integer aOffset, @RequestParam("limit") Integer aLimit) {
        return Optional.ofNullable(genreService.findById(aGenreId))
                .map(g -> new BooksPageDTO(bookService.getBooksByGenreId(g.getId(), aOffset, aLimit)))
                .orElse(null);
    }

    @ResponseBody
    @PostMapping(value = "/author/{authorId}", params = {"offset", "limit"})
    public BooksPageDTO getBooksByAuthorPage(@PathVariable(value = "authorId", required = false) Long aAuthorId, @RequestParam("offset") Integer aOffset, @RequestParam("limit") Integer aLimit) {
        return Optional.ofNullable(authorService.findById(aAuthorId))
                .map(a -> new BooksPageDTO(bookService.getBooksByAuthorId(a.getId(), aOffset, aLimit)))
                .orElse(null);
    }

}
