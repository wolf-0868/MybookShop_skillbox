package com.example.bookshop.controllers;

import com.example.bookshop.data.dto.SearchWordDTO;
import com.example.bookshop.security.BookshopUserRegistrar;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@ControllerAdvice
public class ResponseWrapperAdvice {

    private final BookshopUserRegistrar bookshopUserRegistrar;

    public ResponseWrapperAdvice(BookshopUserRegistrar aBookshopUserRegistrar) {
        bookshopUserRegistrar = aBookshopUserRegistrar;
    }

    @ModelAttribute
    protected void addAttributes(Model aModel) {
        aModel.addAttribute("searchWordDTO", new SearchWordDTO());
        Optional.ofNullable(bookshopUserRegistrar.getCurrentUser())
                .ifPresent(user -> aModel.addAttribute("currentUserDTO", user));
    }

}
