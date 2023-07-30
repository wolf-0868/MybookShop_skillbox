package com.example.bookshop.controllers.admin;

import com.example.bookshop.controllers.ControllerUtilities;
import com.example.bookshop.data.dto.drafts.DraftAuthorDTO;
import com.example.bookshop.exceptions.BookshopException;
import com.example.bookshop.services.admin.AdminAuthorService;
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
public class AdminAuthorPageController {

    private static final String ADMIN_AUTHORS_URL = "/admin/authors";

    private final AdminAuthorService adminAuthorService;

    @GetMapping(value = "/admin/authors")
    public String adminAuthorsPage(
            Model aModel,
            @RequestParam(required = false, name = "keyword") String aKeyword,
            @RequestParam(defaultValue = "1", name = "page") int aPage,
            @RequestParam(defaultValue = "10", name = "size") int aSize) {
        Pageable paging = PageRequest.of(aPage - 1, aSize, Sort.by("id"));
        Page<DraftAuthorDTO> pageAuthors;
        try {
            if (aKeyword == null) {
                pageAuthors = adminAuthorService.getPageAuthors(paging);
            } else {
                pageAuthors = adminAuthorService.getPageOfSearchResultAuthors(aKeyword, paging);
                aModel.addAttribute("keyword", aKeyword);
            }
            aModel.addAttribute("authors", pageAuthors.getContent());
            Utilities.addPageableAttributes(aModel, pageAuthors);
        } catch (Exception e) {
            Utilities.addMessageAttribute(aModel, e.getMessage());
        }
        return ADMIN_AUTHORS_URL;
    }

    @GetMapping(value = "/admin/authors/new")
    public String newAuthorFormPage(Model aModel) {
        aModel.addAttribute("author", new DraftAuthorDTO());
        aModel.addAttribute("pageTitle", "Create new Author");

        return "/admin/author_form";
    }

    @PostMapping(value = "/admin/authors/save")
    public String saveAuthorFormPage(DraftAuthorDTO aAuthor, RedirectAttributes aRedirectAttributes) {
        try {
            adminAuthorService.saveAuthor(aAuthor);
            Utilities.addMessageAttribute(aRedirectAttributes, "The Author has been saved successfully!");
        } catch (BookshopException e) {
            Utilities.addMessageAttribute(aRedirectAttributes, e.getMessage());
        }
        return ControllerUtilities.REDIRECT + ADMIN_AUTHORS_URL;
    }

    @GetMapping(value = "/admin/authors/edit/{id}")
    public String editBookFormPage(@PathVariable(value = "id") Long aId, Model aModel, RedirectAttributes aRedirectAttributes) {
        try {
            aModel.addAttribute("author", adminAuthorService.findAuthorById(aId));
            aModel.addAttribute("pageTitle", "Edit Author (ID: " + aId + ")");

            return "/admin/author_form";
        } catch (BookshopException e) {
            Utilities.addMessageAttribute(aRedirectAttributes, e.getMessage());
            return ControllerUtilities.REDIRECT + ADMIN_AUTHORS_URL;
        }
    }

    @GetMapping(value = "/admin/authors/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long aId, RedirectAttributes aRedirectAttributes) {
        try {
            adminAuthorService.deleteAuthorById(aId);
            Utilities.addMessageAttribute(aRedirectAttributes, "The Author with id=" + aId + " has been deleted successfully!");
        } catch (Exception e) {
            Utilities.addMessageAttribute(aRedirectAttributes, e.getMessage());
        }
        return ControllerUtilities.REDIRECT + ADMIN_AUTHORS_URL;
    }

    @PostMapping(value = "/admin/authors/{id}/image/upload")
    public String changeImageForAuthor(@PathVariable("id") Long aId, @RequestParam("image") MultipartFile aFile, RedirectAttributes aRedirectAttributes) {
        try {
            adminAuthorService.changeImageForAuthor(aFile, aId);
        } catch (IOException | BookshopException e) {
            Utilities.addMessageAttribute(aRedirectAttributes, e.getMessage());
        }
        return ControllerUtilities.REDIRECT + ADMIN_AUTHORS_URL + "/edit/" + aId.toString();
    }

}
