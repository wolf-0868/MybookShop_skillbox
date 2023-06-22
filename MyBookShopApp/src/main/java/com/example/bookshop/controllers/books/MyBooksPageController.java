package com.example.bookshop.controllers.books;

import com.example.bookshop.data.dto.page.BooksPageDTO;
import com.example.bookshop.exceptions.UserNotFountException;
import com.example.bookshop.security.BookshopUserRegistrar;
import com.example.bookshop.services.Book2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MyBooksPageController {

    private final Book2UserService book2UserService;
    private final BookshopUserRegistrar bookshopUserRegistrar;

    @GetMapping(value = "/my")
    public String getMyPage(Model aModel) throws UserNotFountException {
        aModel.addAttribute("bookBuysPage", new BooksPageDTO(book2UserService.getUnreadBooks(bookshopUserRegistrar.getCurrentIdUser())));
        return "my";
    }

    @GetMapping(value = "/myarchive")
    public String getMyArchivePage(Model aModel) throws UserNotFountException {
        aModel.addAttribute("bookArchivesPage", new BooksPageDTO(book2UserService.getArchivedBooks(bookshopUserRegistrar.getCurrentIdUser())));
        return "myarchive";
    }

}
