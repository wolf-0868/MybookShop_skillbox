package com.example.bookshop.controllers;

import com.example.bookshop.data.dto.SearchWordDTO;
import com.example.bookshop.exceptions.BookshopException;
import com.example.bookshop.exceptions.UserNotFountException;
import com.example.bookshop.security.BookshopUserRegistrar;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@Log
@ControllerAdvice
@RequiredArgsConstructor
public class ResponseWrapperAdvice {

    private final BookshopUserRegistrar bookshopUserRegistrar;

    @ModelAttribute
    protected void addAttributes(Model aModel) {
        aModel.addAttribute("searchWordDTO", new SearchWordDTO());
        try {
            aModel.addAttribute("currentUserDTO", bookshopUserRegistrar.getCurrentUser());
        } catch (UserNotFountException e) {
            log.fine("Пользователь не авторизирован");
        }
    }

    @ExceptionHandler(value = BookshopException.class)
    public String handleException(BookshopException aException, Model aModel) {
        log.warning(aException.getMessage());
        aModel.addAttribute("errorExClassName", aException.getClass().getName());
        aModel.addAttribute("errorExMessage", aException.getMessage());
        return "/book_error";
    }

}
