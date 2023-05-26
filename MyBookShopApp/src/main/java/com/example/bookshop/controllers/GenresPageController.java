package com.example.bookshop.controllers;

import com.example.bookshop.data.dto.GenreDTO;
import com.example.bookshop.data.dto.SlugDTO;
import com.example.bookshop.exceptions.DataNotFoundException;
import com.example.bookshop.services.BookService;
import com.example.bookshop.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/genres", method = RequestMethod.GET)
public class GenresPageController {

    private GenreService genreService;
    private BookService bookService;

    @Autowired
    public GenresPageController(GenreService aGenreService, BookService aBookService) {
        genreService = aGenreService;
        bookService = aBookService;
    }

    @ModelAttribute
    protected void addAttributes(Model aModel) {
        aModel.addAttribute("genreDTO", GenreDTO.builder()
                .id(-1L)
                .build());
        aModel.addAttribute("genreValues", genreService.getAllGenres());
        aModel.addAttribute("genreBooks", List.of());
        aModel.addAttribute("genreMapValues", genreService.getAllGenres()
                .stream()
                .collect(Collectors.groupingBy(g -> g)));
    }

    @GetMapping(value = {"", "/index"})
    public String indexPage() {
        return "genres/index";
    }

    @GetMapping(value = "/slug")
    public String slugPage() {
        return "genres/slug";
    }

    @GetMapping(value = "/{genreSlug}")
    public String getBooksByGenrePage(@PathVariable(value = "genreSlug") SlugDTO aSlug, Model aModel) throws DataNotFoundException {
        GenreDTO genre = genreService.findBySlug(aSlug.getName());
        aModel.addAttribute("genreDTO", genre);
        aModel.addAttribute("genreBooks", bookService.getBooksByGenreId(genre.getId(), 0, 20));
        return "genres/slug";
    }

}
