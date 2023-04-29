package com.example.bookshop.controllers;

import com.example.bookshop.controllers.data.dto.AuthorDTO;
import com.example.bookshop.controllers.data.dto.SlugDTO;
import com.example.bookshop.services.AuthorService;
import com.example.bookshop.services.BookService;
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
@RequestMapping(value = "/authors", method = RequestMethod.GET)
public class AuthorPageController extends AbstractPageController {

    private final AuthorService authorService;

    private final BookService bookService;

    @Autowired
    public AuthorPageController(AuthorService aAuthorService, BookService aBookService) {
        authorService = aAuthorService;
        bookService = aBookService;
    }

    @Override
    public void addAttributes(Model aModel) {
        super.addAttributes(aModel);
        aModel.addAttribute("authorBooks", new ArrayList<>());
        aModel.addAttribute("authorDTO", AuthorDTO.builder()
                .id(-1L)
                .build());
        aModel.addAttribute("authorMap", authorService.getAllAuthors()
                .stream()
                .collect(Collectors.groupingBy(a -> a.getFullname().substring(0, 1))));
    }

    @GetMapping(value = {"", "/index"})
    public String indexPage() {
        return "authors/index";
    }

    @GetMapping(value = {"/{authorSlug}", "books/{authorSlug}"})
    public String booksByAuthorPage(@PathVariable(value = "authorSlug", required = false) SlugDTO aSlug, Model aModel) {
        if (aSlug != null) {
            AuthorDTO author = authorService.findBySlug(aSlug.getName());
            if (author != null) {
                aModel.addAttribute("authorDTO", author);
                aModel.addAttribute("authorBooks", bookService.getBooksByAuthorId(author.getId(), 0, 6));
            }
        }
        return "authors/slug";
    }

}
