package com.example.bookshop.controllers.genres;

import com.example.bookshop.data.dto.GenreDTO;
import com.example.bookshop.data.dto.SlugDTO;
import com.example.bookshop.exceptions.DataNotFoundException;
import com.example.bookshop.services.BookService;
import com.example.bookshop.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class GenrePageController {

    private final GenreService genreService;
    private final BookService bookService;

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

    @GetMapping(value = {"/genres", "/genres/index"})
    public String indexPage() {
        return "genres/index";
    }

    @GetMapping(value = "/genres/slug")
    public String slugPage() {
        return "genres/slug";
    }

    @GetMapping(value = "/genres/{genreSlug}")
    public String getBooksByGenrePage(@PathVariable(value = "genreSlug") SlugDTO aSlug, Model aModel) throws DataNotFoundException {
        GenreDTO genre = genreService.findBySlug(aSlug.getName());
        aModel.addAttribute("genreDTO", genre);
        aModel.addAttribute("genreBooks", bookService.getPageByGenreId(genre.getId(), 0, 20));
        return "genres/slug";
    }

}
