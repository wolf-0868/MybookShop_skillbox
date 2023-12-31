package com.example.bookshop.controllers;

import com.example.bookshop.data.dto.SearchWordDTO;
import com.example.bookshop.data.dto.page.BooksPageDTO;
import com.example.bookshop.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class SearchPageController {

    private final BookService bookService;

    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String searchPage(
            @PathVariable(value = "searchWord") SearchWordDTO aSearchWord,
            Model aModel) {
        aModel.addAttribute("searchWordDTO", aSearchWord);
        aModel.addAttribute("searchResult", bookService.getPageOfSearchResultBooks(aSearchWord.getExample(), 0, 20));
        return "search/index";
    }

    @ResponseBody
    @GetMapping(value = "/search/page/{searchWord}")
    public BooksPageDTO getNextSearchPage(
            @RequestParam("offset") Integer aOffset,
            @RequestParam("limit") Integer aLimit,
            @PathVariable(value = "searchWord") SearchWordDTO aSearchWord) {
        return new BooksPageDTO(bookService.getPageOfSearchResultBooks(aSearchWord.getExample(), aOffset, aLimit).getContent());
    }

}
