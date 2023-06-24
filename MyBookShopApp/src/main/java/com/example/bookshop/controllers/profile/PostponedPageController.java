package com.example.bookshop.controllers.profile;

import com.example.bookshop.data.dto.page.BooksPageDTO;
import com.example.bookshop.data.entities.enums.BookBindingType;
import com.example.bookshop.exceptions.UserNotFountException;
import com.example.bookshop.security.BookshopUserRegistrar;
import com.example.bookshop.services.Book2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/postponed", method = RequestMethod.GET)
@RequiredArgsConstructor
public class PostponedPageController {

    private final Book2UserService book2UserService;
    private final BookshopUserRegistrar bookshopUserRegistrar;

    @GetMapping
    public String postponedPage(Model aModel) throws UserNotFountException {
        aModel.addAttribute("bookPostponesPage", new BooksPageDTO(book2UserService.findBooksByBindingTypeForUser(bookshopUserRegistrar.getCurrentIdUser(), BookBindingType.KEPT)));
        return "postponed";
    }

}
