package com.example.bookshop.controllers;

import com.example.bookshop.data.dto.SearchWordDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class AbstractPageController {

    @ModelAttribute
    protected void addAttributes(Model aModel) {
        aModel.addAttribute("searchWordDTO", new SearchWordDTO());
    }

}
