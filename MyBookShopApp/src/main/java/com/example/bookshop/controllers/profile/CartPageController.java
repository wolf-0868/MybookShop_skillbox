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

@Controller
@RequiredArgsConstructor
public class CartPageController {

    private final Book2UserService book2UserService;
    private final BookshopUserRegistrar bookshopUserRegistrar;

    @GetMapping(value = "/cart")
    public String cartPage(Model aModel) throws UserNotFountException {
        aModel.addAttribute("bookCartsPage", new BooksPageDTO(book2UserService.findBooksByBindingTypeForUser(bookshopUserRegistrar.getCurrentIdUser(), BookBindingType.CART)));
        return "cart";
    }

}
