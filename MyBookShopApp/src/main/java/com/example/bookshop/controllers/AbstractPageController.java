package com.example.bookshop.controllers;

import com.example.bookshop.model.SearchWordModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class AbstractPageController {

    @ModelAttribute
    protected void addAttributes(Model aModel) {
        aModel.addAttribute("searchWordModel", new SearchWordModel());
    }

}
