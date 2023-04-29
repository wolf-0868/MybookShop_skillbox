package com.example.bookshop.controllers.books;

import com.example.bookshop.controllers.data.dto.BooksPageDTO;
import com.example.bookshop.services.BookService;
import com.example.bookshop.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(value = "/books/genre", method = {RequestMethod.GET, RequestMethod.POST})
public class GenreBooksPageController {

    private final BookService bookService;

    private final GenreService genreService;

    @Autowired
    public GenreBooksPageController(BookService aBookService, GenreService aGenreService) {
        bookService = aBookService;
        genreService = aGenreService;
    }

    @ResponseBody
    @PostMapping(value = "/{genreId}", params = {"offset", "limit"})
    public BooksPageDTO getBooksByGenrePage(
            @PathVariable(value = "genreId", required = false) Long aGenreId,
            @RequestParam("offset") Integer aOffset,
            @RequestParam("limit") Integer aLimit) {
        return Optional.ofNullable(genreService.findById(aGenreId))
                .map(g -> new BooksPageDTO(bookService.getBooksByGenreId(g.getId(), aOffset, aLimit)))
                .orElse(null);
    }

}