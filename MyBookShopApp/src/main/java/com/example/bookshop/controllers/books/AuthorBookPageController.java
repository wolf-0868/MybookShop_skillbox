package com.example.bookshop.controllers.books;

import com.example.bookshop.data.dto.AuthorDTO;
import com.example.bookshop.data.dto.SlugDTO;
import com.example.bookshop.data.dto.page.BooksPageDTO;
import com.example.bookshop.exceptions.DataNotFoundException;
import com.example.bookshop.services.AuthorService;
import com.example.bookshop.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/books/author", method = {RequestMethod.GET, RequestMethod.POST})
public class AuthorBookPageController {

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public AuthorBookPageController(BookService aBookService, AuthorService aAuthorService) {
        bookService = aBookService;
        authorService = aAuthorService;
    }

    @ModelAttribute
    public void addAttributes(Model aModel) {
        aModel.addAttribute("authorBooks", List.of());
        aModel.addAttribute("authorDTO", AuthorDTO.builder()
                .id(-1L)
                .build());
    }

    @GetMapping(value = "/{authorSlug}")
    public String booksByAuthorPage(@PathVariable(value = "authorSlug") SlugDTO aSlug, Model aModel) throws DataNotFoundException {
        AuthorDTO author = authorService.findBySlug(aSlug.getName());
        aModel.addAttribute("authorDTO", author);
        aModel.addAttribute("authorBooks", bookService.getBooksByAuthorId(author.getId(), 0, 20));
        return "books/author";
    }

    @ResponseBody
    @PostMapping(value = "/{authorId}", params = {"offset", "limit"})
    public BooksPageDTO getBooksByAuthorPage(
            @PathVariable(value = "authorId", required = false) Long aAuthorId,
            @RequestParam("offset") Integer aOffset,
            @RequestParam("limit") Integer aLimit) throws DataNotFoundException {
        return new BooksPageDTO(bookService.getBooksByAuthorId(authorService.findById(aAuthorId).getId(), aOffset, aLimit));
    }

}
