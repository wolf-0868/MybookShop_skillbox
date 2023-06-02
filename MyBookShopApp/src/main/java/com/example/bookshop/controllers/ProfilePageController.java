package com.example.bookshop.controllers;

import com.example.bookshop.exceptions.UserNotFountException;
import com.example.bookshop.security.BookshopUserRegistrar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/profile", method = RequestMethod.GET)
@RequiredArgsConstructor
public class ProfilePageController {

    private final BookshopUserRegistrar bookshopUserRegistrar;

    @GetMapping
    public String getProfilePage(Model aModel) throws UserNotFountException {
        aModel.addAttribute("draftUserDTO", bookshopUserRegistrar.getDraftCurrentUser());
        return "profile";
    }

}
