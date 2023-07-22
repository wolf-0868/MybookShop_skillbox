package com.example.bookshop.controllers;

import com.example.bookshop.data.dto.GenreAndTag;
import com.example.bookshop.data.dto.GenreDTO;
import com.example.bookshop.services.BookService;
import com.example.bookshop.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/", method = RequestMethod.GET)
@RequiredArgsConstructor
public class MainPageController {

    private final BookService bookService;
    private final GenreService genreService;

    private static String getLevelTagGenre(GenreDTO aGenre) {
        int count = aGenre.getCountBooks();
        if (count <= 5) {
            return "Tag_xs";
        } else if ((count > 5) && (count <= 10)) {
            return "Tag_sm";
        } else if ((count > 10) && (count <= 15)) {
            return "";
        } else if ((count > 15) && (count <= 20)) {
            return "Tag_md";
        } else if (count > 20) {
            return "Tag_lg";
        }
        return "";
    }

    @ModelAttribute
    public void addAttributes(Model aModel) {
        aModel.addAttribute("recommendedBooks", bookService.getPageRecommendedBooks(0, 6));
        aModel.addAttribute("recentBooks", bookService.getPageOfRecentBooks(0, 6));
        aModel.addAttribute("popularBooks", bookService.getPageOfPopularBooks(0, 6));
        aModel.addAttribute("genresMap", genreService.getAllGenres()
                .stream()
                .map(v -> GenreAndTag.builder()
                        .tag(getLevelTagGenre(v))
                        .value(v)
                        .build())
                .collect(Collectors.toList()));
    }

    @GetMapping(value = {"", "/index"})
    public String mainPage(Model aModel) {
        return "index";
    }

}