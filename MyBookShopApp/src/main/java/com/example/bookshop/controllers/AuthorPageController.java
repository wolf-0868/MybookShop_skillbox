package com.example.bookshop.controllers;

import com.example.bookshop.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/authors", method = RequestMethod.GET)
public class AuthorPageController extends AbstractPageController {

    private final AuthorService authorService;

    @Autowired
    public AuthorPageController(AuthorService aAuthorService) {
        authorService = aAuthorService;
    }

    @Override
    public void addAttributes(Model aModel) {
        super.addAttributes(aModel);
        aModel.addAttribute("authorMap", authorService.getAllAuthors()
                .stream()
                .collect(Collectors.groupingBy(a -> a.getFullname().substring(0, 1))));
    }

    @GetMapping(value = {"", "/index"})
    public String indexPage() {
        return "authors/index";
    }

}
