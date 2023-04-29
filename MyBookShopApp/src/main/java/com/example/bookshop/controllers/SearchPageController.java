package com.example.bookshop.controllers;

import com.example.bookshop.controllers.data.dto.BooksPageDTO;
import com.example.bookshop.controllers.data.dto.SearchWordDTO;
import com.example.bookshop.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping(value = "/search")
public class SearchPageController extends AbstractPageController {

    private final BookService bookService;

    @Autowired
    public SearchPageController(BookService aBookService) {
        bookService = aBookService;
    }

    @GetMapping(value = {"", "/{searchWord}"})
    public String searchPage(@PathVariable(value = "searchWord", required = false) SearchWordDTO aSearchWord, Model aModel) {
        if (aSearchWord == null) {
            aModel.addAttribute("searchResult", new ArrayList<>());
        } else {
            aModel.addAttribute("searchWordDTO", aSearchWord);
            aModel.addAttribute("searchResult", bookService.getPageOfSearchResultBooks(aSearchWord.getExample(), 0, 20));
        }
        return "search/index";
    }

    @ResponseBody
    @GetMapping(value = "/page/{searchWord}")
    public BooksPageDTO getNextSearchPage(
            @RequestParam("offset") Integer aOffset,
            @RequestParam("limit") Integer aLimit,
            @PathVariable(value = "searchWord", required = false) SearchWordDTO aSearchWord) {
        return new BooksPageDTO(bookService.getPageOfSearchResultBooks(aSearchWord.getExample(), aOffset, aLimit));
    }

}
