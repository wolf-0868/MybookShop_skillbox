package com.example.bookshop.controllers;

import com.example.bookshop.data.dto.AuthorDTO;
import com.example.bookshop.data.dto.SlugDTO;
import com.example.bookshop.exceptions.DataNotFoundException;
import com.example.bookshop.services.AuthorService;
import com.example.bookshop.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/authors", method = RequestMethod.GET)
public class AuthorPageController {

    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public AuthorPageController(AuthorService aAuthorService, BookService aBookService) {
        authorService = aAuthorService;
        bookService = aBookService;
    }

    @ModelAttribute
    public void addAttributes(Model aModel) {
        aModel.addAttribute("authorMap", authorService.getAllAuthors()
                .stream()
                .collect(Collectors.groupingBy(a -> a.getFullname().substring(0, 1))));
    }

    @GetMapping(value = {"", "/index"})
    public String indexPage() {
        return "authors/index";
    }

    @GetMapping(value = {"/{authorSlug}", "books/{authorSlug}"})
    public String booksByAuthorPage(@PathVariable(value = "authorSlug") SlugDTO aSlug, Model aModel) throws DataNotFoundException {
        AuthorDTO author = authorService.findBySlug(aSlug.getName());
        aModel.addAttribute("authorDTO", author);
        aModel.addAttribute("authorBooks", bookService.getBooksByAuthorId(author.getId(), 0, 6));
        return "authors/slug";
    }

}
