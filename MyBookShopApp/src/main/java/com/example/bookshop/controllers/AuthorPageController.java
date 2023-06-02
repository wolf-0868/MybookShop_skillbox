package com.example.bookshop.controllers;

import com.example.bookshop.data.dto.AuthorDTO;
import com.example.bookshop.data.dto.SlugDTO;
import com.example.bookshop.exceptions.DataNotFoundException;
import com.example.bookshop.services.AuthorService;
import com.example.bookshop.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/authors", method = RequestMethod.GET)
@RequiredArgsConstructor
public class AuthorPageController {

    private final AuthorService authorService;
    private final BookService bookService;

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
        aModel.addAttribute("authorBooks", bookService.getPageByAuthorId(author.getId(), 0, 6));
        return "authors/slug";
    }

}
