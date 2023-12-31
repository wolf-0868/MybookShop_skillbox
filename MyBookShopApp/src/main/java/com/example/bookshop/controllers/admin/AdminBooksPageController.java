package com.example.bookshop.controllers.admin;

import com.example.bookshop.controllers.ControllerUtilities;
import com.example.bookshop.data.dto.drafts.DraftBookDTO;
import com.example.bookshop.exceptions.BookshopException;
import com.example.bookshop.services.admin.AdminBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class AdminBooksPageController {

    private static final String ADMIN_BOOKS_URL = "/admin/books";

    private final AdminBookService adminBookService;

    @GetMapping(value = {"/admin/books", "/admin"})
    public String adminBooksPage(
            Model aModel,
            @RequestParam(required = false, name = "keyword") String aKeyword,
            @RequestParam(defaultValue = "1", name = "page") int aPage,
            @RequestParam(defaultValue = "10", name = "size") int aSize) {
        Page<DraftBookDTO> pageBooks = null;
        Pageable paging = PageRequest.of(aPage - 1, aSize, Sort.by("id"));

        try {
            if (aKeyword == null) {
                pageBooks = adminBookService.getPageBooks(paging);
            } else {
                pageBooks = adminBookService.getPageOfSearchResultBooks(aKeyword, paging);
                aModel.addAttribute("keyword", aKeyword);
            }
            aModel.addAttribute("books", pageBooks.getContent());
            Utilities.addPageableAttributes(aModel, pageBooks);

        } catch (Exception e) {
            Utilities.addMessageAttribute(aModel, e.getMessage());
        }
        return ADMIN_BOOKS_URL;
    }

    @GetMapping(value = "/admin/books/new")
    public String newBookFormPage(Model aModel) {
        aModel.addAttribute("book", new DraftBookDTO());
        aModel.addAttribute("allAuthors", adminBookService.getAuthorsBookPayload());
        aModel.addAttribute("pageTitle", "Create new Book");

        return "/admin/book_form";
    }

    @PostMapping(value = "/admin/books/save")
    public String saveBookFormPage(DraftBookDTO aBook, RedirectAttributes aRedirectAttributes) {
        try {
            adminBookService.saveBook(aBook);
            Utilities.addMessageAttribute(aRedirectAttributes, "The Book has been saved successfully!");
        } catch (BookshopException e) {
            Utilities.addMessageAttribute(aRedirectAttributes, e.getMessage());
        }
        return ControllerUtilities.REDIRECT + ADMIN_BOOKS_URL;
    }

    @GetMapping(value = "/admin/books/edit/{id}")
    public String editBookFormPage(@PathVariable(value = "id") Long aId, Model aModel, RedirectAttributes aRedirectAttributes) {
        try {
            aModel.addAttribute("book", adminBookService.findBookById(aId));
            aModel.addAttribute("allAuthors", adminBookService.getAuthorsBookPayload());
            aModel.addAttribute("pageTitle", "Edit Book (ID: " + aId + ")");

            return "/admin/book_form";
        } catch (BookshopException e) {
            Utilities.addMessageAttribute(aRedirectAttributes, e.getMessage());
            return ControllerUtilities.REDIRECT + ADMIN_BOOKS_URL;
        }
    }

    @GetMapping(value = "/admin/books/delete/{id}")
    public String deleteBook(@PathVariable("id") Long aId, RedirectAttributes aRedirectAttributes) {
        try {
            adminBookService.deleteBookById(aId);
            Utilities.addMessageAttribute(aRedirectAttributes, "The Book with id=" + aId + " has been deleted successfully!");
        } catch (Exception e) {
            Utilities.addMessageAttribute(aRedirectAttributes, e.getMessage());
        }
        return ControllerUtilities.REDIRECT + ADMIN_BOOKS_URL;
    }

    @PostMapping(value = "/admin/books/{id}/image/upload")
    public String changeImageForBook(@PathVariable("id") Long aId, @RequestParam("image") MultipartFile aFile, RedirectAttributes aRedirectAttributes) {
        try {
            adminBookService.changeImageForBook(aFile, aId);
        } catch (IOException | BookshopException e) {
            Utilities.addMessageAttribute(aRedirectAttributes, e.getMessage());
        }
        return ControllerUtilities.REDIRECT + ADMIN_BOOKS_URL + "edit/" + aId.toString();
    }

}
