package com.example.bookshop.controllers.admin;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@UtilityClass
class Utilities {

    private static final String MESSAGE_ATTRIBUTE = "message";

    static void addMessageAttribute(RedirectAttributes aRedirectAttributes, String aMessageText) {
        aRedirectAttributes.addFlashAttribute(MESSAGE_ATTRIBUTE, aMessageText);
    }

    static void addMessageAttribute(Model aModel, String aMessageText) {
        aModel.addAttribute(MESSAGE_ATTRIBUTE, aMessageText);
    }

    static void addPageableAttributes(Model aModel, Page<?> aPage) {
        aModel.addAttribute("currentPage", aPage.getNumber() + 1);
        aModel.addAttribute("totalItems", aPage.getTotalElements());
        aModel.addAttribute("totalPages", aPage.getTotalPages());
        aModel.addAttribute("pageSize", aPage.getSize());
    }

}
