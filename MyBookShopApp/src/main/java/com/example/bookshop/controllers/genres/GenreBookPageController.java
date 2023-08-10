package com.example.bookshop.controllers.genres;

import com.example.bookshop.data.dto.page.BooksPageDTO;
import com.example.bookshop.exceptions.DataNotFoundException;
import com.example.bookshop.services.BookService;
import com.example.bookshop.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class GenreBookPageController {

    private final GenreService genreService;
    private final BookService bookService;

    @ResponseBody
    @GetMapping(value = "/books/genre/{genreId}", params = {"offset", "limit"})
    public BooksPageDTO getBooksByGenrePage(
            @PathVariable(value = "genreId") Long aGenreId,
            @RequestParam("offset") Integer aOffset,
            @RequestParam("limit") Integer aLimit) throws DataNotFoundException {
        return new BooksPageDTO(bookService.getPageByGenreId(genreService.findById(aGenreId).getId(), aOffset, aLimit));
    }

}
