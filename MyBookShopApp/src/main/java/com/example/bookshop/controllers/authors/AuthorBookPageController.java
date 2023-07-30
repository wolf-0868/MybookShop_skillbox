package com.example.bookshop.controllers.authors;

import com.example.bookshop.data.dto.AuthorDTO;
import com.example.bookshop.data.dto.SlugDTO;
import com.example.bookshop.data.dto.page.BooksPageDTO;
import com.example.bookshop.exceptions.DataNotFoundException;
import com.example.bookshop.services.AuthorService;
import com.example.bookshop.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorBookPageController {

    private final BookService bookService;
    private final AuthorService authorService;

    @ModelAttribute
    public void addAttributes(Model aModel) {
        aModel.addAttribute("authorBooks", List.of());
        aModel.addAttribute("authorDTO", AuthorDTO.builder()
                .id(-1L)
                .build());
    }

    @GetMapping(value = "/books/author/{authorSlug}")
    public String booksByAuthorPage(@PathVariable(value = "authorSlug") SlugDTO aSlug, Model aModel) throws DataNotFoundException {
        AuthorDTO author = authorService.findBySlug(aSlug.getName());
        aModel.addAttribute("authorDTO", author);
        aModel.addAttribute("authorBooks", bookService.getPageByAuthorId(author.getId(), 0, 20));
        return "books/author";
    }

    @ResponseBody
    @PostMapping(value = "/books/author/{authorId}", params = {"offset", "limit"})
    public BooksPageDTO getBooksByAuthorPage(
            @PathVariable(value = "authorId", required = false) Long aAuthorId,
            @RequestParam("offset") Integer aOffset,
            @RequestParam("limit") Integer aLimit) throws DataNotFoundException {
        return new BooksPageDTO(bookService.getPageByAuthorId(authorService.findById(aAuthorId).getId(), aOffset, aLimit));
    }

}
