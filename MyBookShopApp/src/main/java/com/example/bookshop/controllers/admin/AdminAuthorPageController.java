package com.example.bookshop.controllers.admin;

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

    private final AdminAuthorService adminAuthorService;

    @GetMapping(value = "/admin/authors")
    public String adminAuthorsPage(
            Model aModel,
            @RequestParam(required = false, name = "keyword") String aKeyword,
            @RequestParam(defaultValue = "1", name = "page") int aPage,
            @RequestParam(defaultValue = "10", name = "size") int aSize) {
        Page<DraftAuthorDTO> pageAuthors = null;
        Pageable paging = PageRequest.of(aPage - 1, aSize, Sort.by("id"));

        try {
            if (aKeyword == null) {
                pageAuthors = adminAuthorService.getPageAuthors(paging);
            } else {
                pageAuthors = adminAuthorService.getPageOfSearchResultAuthors(aKeyword, paging);
                aModel.addAttribute("keyword", aKeyword);
            }
            aModel.addAttribute("authors", pageAuthors.getContent());
            aModel.addAttribute("currentPage", pageAuthors.getNumber() + 1);
            aModel.addAttribute("totalItems", pageAuthors.getTotalElements());
            aModel.addAttribute("totalPages", pageAuthors.getTotalPages());
            aModel.addAttribute("pageSize", pageAuthors.getSize());

        } catch (Exception e) {
            aModel.addAttribute("message", e.getMessage());
        }
        return "/admin/authors";
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
            aRedirectAttributes.addFlashAttribute("message", "The Author has been saved successfully!");
        } catch (BookshopException e) {
            aRedirectAttributes.addAttribute("message", e.getMessage());
        }
        return "redirect:/admin/authors";
    }

    @GetMapping(value = "/admin/authors/edit/{id}")
    public String editBookFormPage(@PathVariable(value = "id") Long aId, Model aModel, RedirectAttributes aRedirectAttributes) {
        try {
            aModel.addAttribute("author", adminAuthorService.findAuthorById(aId));
            aModel.addAttribute("pageTitle", "Edit Author (ID: " + aId + ")");

            return "/admin/author_form";
        } catch (BookshopException e) {
            aRedirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/authors";
        }
    }

    @GetMapping(value = "/admin/authors/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long aId, RedirectAttributes aRedirectAttributes) {
        try {
            adminAuthorService.deleteAuthorById(aId);
            aRedirectAttributes.addFlashAttribute("message", "The Author with id=" + aId + " has been deleted successfully!");
        } catch (Exception e) {
            aRedirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admin/authors";
    }

    @PostMapping(value = "/admin/authors/{id}/image/upload")
    public String changeImageForAuthor(@PathVariable("id") Long aId, @RequestParam("image") MultipartFile aFile, RedirectAttributes aRedirectAttributes) {
        try {
            adminAuthorService.changeImageForAuthor(aFile, aId);
        } catch (IOException | BookshopException e) {
            aRedirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admin/authors/edit/" + aId.toString();
    }

}
