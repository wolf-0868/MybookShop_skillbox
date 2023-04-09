package com.example.bookshop.controllers;

import com.example.bookshop.data.Author;
import com.example.bookshop.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AuthorPageController {

    private final AuthorService _authorService;

    @Autowired
    public AuthorPageController(AuthorService aAuthorService) {
        _authorService = aAuthorService;
    }

    @ModelAttribute("activePage")
    public ActivePageType activePage() {
        return ActivePageType.AUTHORS;
    }

    @ModelAttribute("authorMap")
    public Map<String, List<Author>> authorMap() {
        return _authorService.getAuthorsData()
                .stream()
                .collect(Collectors.groupingBy(a -> a.getFullname().substring(0, 1)));
    }

    @GetMapping({"/authors", "authors/index.html"})
    public String indexPage() {
        return "authors/index";
    }

}
