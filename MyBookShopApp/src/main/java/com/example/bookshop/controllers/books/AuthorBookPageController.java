package com.example.bookshop.controllers.books;

import com.example.bookshop.controllers.AbstractPageController;
import com.example.bookshop.data.dto.AuthorDTO;
import com.example.bookshop.data.dto.BooksPageDTO;
import com.example.bookshop.data.dto.SlugDTO;
import com.example.bookshop.services.AuthorService;
import com.example.bookshop.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping(value = "/books/author", method = {RequestMethod.GET, RequestMethod.POST})
public class AuthorBookPageController extends AbstractPageController {

    private final BookService bookService;

    private final AuthorService authorService;

    @Autowired
    public AuthorBookPageController(BookService aBookService, AuthorService aAuthorService) {
        bookService = aBookService;
        authorService = aAuthorService;
    }

    @Override
    protected void addAttributes(Model aModel) {
        super.addAttributes(aModel);
        aModel.addAttribute("authorBooks", new ArrayList<>());
        aModel.addAttribute("authorDTO", AuthorDTO.builder()
                .id(-1L)
                .build());
    }

    @GetMapping(value = "/{authorSlug}")
    public String booksByAuthorPage(
            @PathVariable(value = "authorSlug", required = false) SlugDTO aSlug,
            Model aModel) {
        if (aSlug != null) {
            AuthorDTO author = authorService.findBySlug(aSlug.getName());
            if (author != null) {
                aModel.addAttribute("authorDTO", author);
                aModel.addAttribute("authorBooks", bookService.getBooksByAuthorId(author.getId(), 0, 20));
            }
        }
        return "books/author";
    }

    @ResponseBody
    @PostMapping(value = "/{authorId}", params = {"offset", "limit"})
    public BooksPageDTO getBooksByAuthorPage(
            @PathVariable(value = "authorId", required = false) Long aAuthorId,
            @RequestParam("offset") Integer aOffset,
            @RequestParam("limit") Integer aLimit) {
        return Optional.ofNullable(authorService.findById(aAuthorId))
                .map(a -> new BooksPageDTO(bookService.getBooksByAuthorId(a.getId(), aOffset, aLimit)))
                .orElse(null);
    }

}
