package com.example.bookshop.controllers.books;

import com.example.bookshop.data.dto.page.BooksPageDTO;
import com.example.bookshop.exceptions.UserNotFountException;
import com.example.bookshop.security.BookshopUserRegistrar;
import com.example.bookshop.services.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/books/recently", method = {RequestMethod.GET, RequestMethod.POST})
@RequiredArgsConstructor
public class RecentlyBooksPageController {

    private final JournalService journalService;
    private final BookshopUserRegistrar userRegistrar;

    @ModelAttribute
    protected void addAttributes(Model aModel) throws UserNotFountException {
        aModel.addAttribute("recentlyBooks", journalService.getPageOfRecentlyBooks(userRegistrar.getCurrentIdUser(), 0, 20));
    }

    @GetMapping
    public String popularPage() {
        return "books/recently";
    }

    @ResponseBody
    @PostMapping(params = {"offset", "limit"})
    public BooksPageDTO getRecentlyBooksPage(
            @RequestParam("offset") Integer aOffset,
            @RequestParam("limit") Integer aLimit) throws UserNotFountException {
        return new BooksPageDTO(journalService.getPageOfRecentlyBooks((userRegistrar.getCurrentIdUser()), aOffset, aLimit));
    }

}
