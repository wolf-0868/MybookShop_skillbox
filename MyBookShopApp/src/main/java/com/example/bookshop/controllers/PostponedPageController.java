package com.example.bookshop.controllers;

import com.example.bookshop.data.dto.page.BooksPageDTO;
import com.example.bookshop.data.entities.enums.BookBindingType;
import com.example.bookshop.exceptions.UserNotFountException;
import com.example.bookshop.security.BookshopUserRegistrar;
import com.example.bookshop.services.Book2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/postponed", method = RequestMethod.GET)
public class PostponedPageController {

    private final Book2UserService book2UserService;
    private final BookshopUserRegistrar bookshopUserRegistrar;

    @Autowired
    public PostponedPageController(Book2UserService aBook2UserService, BookshopUserRegistrar aBookshopUserRegistrar) {
        book2UserService = aBook2UserService;
        bookshopUserRegistrar = aBookshopUserRegistrar;
    }

    @GetMapping
    public String postponedPage(Model aModel) throws UserNotFountException {
        aModel.addAttribute("bookPostponesPage", new BooksPageDTO(book2UserService.getBooksByBindingTypeForUser(bookshopUserRegistrar.getCurrentIdUser(), BookBindingType.KEPT)));
        return "postponed";
    }

}
