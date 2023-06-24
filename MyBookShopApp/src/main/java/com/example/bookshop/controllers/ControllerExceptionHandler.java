package com.example.bookshop.controllers;

import com.example.bookshop.exceptions.BookshopException;
import lombok.extern.java.Log;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = BookshopException.class)
    public String handleException(BookshopException aException, Model aModel) {
        log.warning(aException.getMessage());
        aModel.addAttribute("errorExClassName", aException.getClass().getName());
        aModel.addAttribute("errorExMessage", aException.getMessage());
        return "/book_error";
    }

}
