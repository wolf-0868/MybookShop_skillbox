package com.example.bookshop.controllers;

import com.example.bookshop.controllers.data.dto.GenreDTO;
import com.example.bookshop.controllers.data.dto.SlugDTO;
import com.example.bookshop.services.BookService;
import com.example.bookshop.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/genres", method = RequestMethod.GET)
public class GenresPageController extends AbstractPageController {

    private GenreService genreService;

    private BookService bookService;

    @Autowired
    public GenresPageController(GenreService aGenreService, BookService aBookService) {
        genreService = aGenreService;
        bookService = aBookService;
    }

    @Override
    protected void addAttributes(Model aModel) {
        super.addAttributes(aModel);
        aModel.addAttribute("genreDTO", GenreDTO.builder()
                .id(-1L)
                .build());
        aModel.addAttribute("genreValues", genreService.getAllGenres());
        aModel.addAttribute("genreBooks", new ArrayList<>());
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
    public String getBooksByGenrePage(@PathVariable(value = "genreSlug", required = false) SlugDTO aSlug, Model aModel) {
        if (aSlug == null) {
            aModel.addAttribute("booksByGenreResult", new ArrayList<>());
        } else {
            GenreDTO genre = genreService.findBySlug(aSlug.getName());
            if (genre != null) {
                aModel.addAttribute("genreDTO", genre);
                aModel.addAttribute("genreBooks", bookService.getBooksByGenreId(genre.getId(), 0, 20));
            }
        }
        return "genres/slug";
    }

}
