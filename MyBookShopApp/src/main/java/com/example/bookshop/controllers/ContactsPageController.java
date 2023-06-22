package com.example.bookshop.controllers;

import com.example.bookshop.data.payloads.MessagePayload;
import com.example.bookshop.exceptions.UserNotFountException;
import com.example.bookshop.security.BookshopUserRegistrar;
import com.example.bookshop.services.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Log
@Controller
@RequiredArgsConstructor
public class ContactsPageController {

    private final BookshopUserRegistrar userRegistrar;
    private final MessageService messageService;

    @GetMapping(value = "/contacts")
    public String contactsPage() {
        return "contacts";
    }

    @ResponseBody
    @PostMapping(value = "/contacts")
    public String handleSendMessage(
            @ModelAttribute("name") String aName,
            @ModelAttribute("mail") String aMail,
            @ModelAttribute("topic") String aSubject,
            @ModelAttribute("message") String aText,
            @ModelAttribute("sendMessage") Object aSendMessage) {
        Long userId = null;
        try {
            userId = userRegistrar.getCurrentIdUser();
        } catch (UserNotFountException e) {
            log.fine("Пользователь не авторизирован");
        }
        messageService.saveNewMessage(MessagePayload.builder()
                .name(aName)
                .mail(aMail)
                .topic(aSubject)
                .message(aText)
                .build(), userId);
        return "redirect:/contacts/";
    }

}
