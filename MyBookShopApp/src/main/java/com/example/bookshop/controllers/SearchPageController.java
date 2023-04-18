package com.example.bookshop.controllers;

import com.example.bookshop.data.entities.BookEntity;
import com.example.bookshop.model.SearchWordModel;
import com.example.bookshop.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/search")
public class SearchPageController extends AbstractPageController {

    private final BookService bookService;

    @Autowired
    public SearchPageController(BookService aBookService) {
        bookService = aBookService;
    }

    @ModelAttribute("searchResult")
    public List<BookEntity> searchResult() {
        return new ArrayList<>();
    }

    @GetMapping(value = {"", "/{searchWord}"})
    public String searchPage(@PathVariable(value = "searchWord", required = false) SearchWordModel aSearchWord, Model aModel) {
        if (aSearchWord != null) {
            aModel.addAttribute("searchWordModel", aSearchWord);
            aModel.addAttribute("searchResult", bookService.getPageOfSearchResultBooks(aSearchWord.getExample(), 0, 20)
                    .getContent());
        }
        return "search/index";
    }

}
