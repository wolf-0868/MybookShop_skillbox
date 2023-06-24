package com.example.bookshop.controllers.genres;

import com.example.bookshop.data.dto.page.BooksPageDTO;
import com.example.bookshop.exceptions.DataNotFoundException;
import com.example.bookshop.services.BookService;
import com.example.bookshop.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/books/genre", method = {RequestMethod.GET, RequestMethod.POST})
@RequiredArgsConstructor
public class GenreBookPageController {

    private final GenreService genreService;
    private final BookService bookService;

    @ResponseBody
    @PostMapping(value = "/{genreId}", params = {"offset", "limit"})
    public BooksPageDTO getBooksByGenrePage(
            @PathVariable(value = "genreId") Long aGenreId,
            @RequestParam("offset") Integer aOffset,
            @RequestParam("limit") Integer aLimit) throws DataNotFoundException {
        return new BooksPageDTO(bookService.getPageByGenreId(genreService.findById(aGenreId).getId(), aOffset, aLimit));
    }

}
