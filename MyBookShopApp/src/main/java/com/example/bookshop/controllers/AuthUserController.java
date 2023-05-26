package com.example.bookshop.controllers;

import com.example.bookshop.data.dto.drafts.DraftUserDTO;
import com.example.bookshop.data.payloads.ContactConfirmationPayload;
import com.example.bookshop.data.payloads.LoginPassConfirmationPayload;
import com.example.bookshop.exceptions.UserNotFountException;
import com.example.bookshop.security.BookshopUserRegistrar;
import com.example.bookshop.security.ConfirmationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthUserController {

    private final BookshopUserRegistrar bookshopUserRegistrar;

    @Autowired
    public AuthUserController(BookshopUserRegistrar aBookshopUserRegistrar) {
        bookshopUserRegistrar = aBookshopUserRegistrar;
    }

    @GetMapping(value = "/signin")
    public String signInPage() {
        return "signin";
    }

    @GetMapping(value = "/signup")
    public String signUpPage(Model aModel) {
        aModel.addAttribute("draftUserDTO", new DraftUserDTO());
        return "signup";
    }

    @ResponseBody
    @PostMapping(value = "/requestContactConfirmation")
    public ConfirmationResponse handleRequestContactConfirmation(@RequestBody ContactConfirmationPayload aPayload) {
        ConfirmationResponse response = new ConfirmationResponse();
        response.setResult("true");
        return response;
    }

    @ResponseBody
    @PostMapping(value = "/approveContact")
    public ConfirmationResponse handleApproveContact(@RequestBody ContactConfirmationPayload aPayload) {
        ConfirmationResponse response = new ConfirmationResponse();
        response.setResult("true");
        return response;
    }

    @ResponseBody
    @PostMapping(value = "/login")
    public ConfirmationResponse handleLogin(@RequestBody LoginPassConfirmationPayload aPayload, HttpServletResponse aHttpServletResponse) {
        ConfirmationResponse response = bookshopUserRegistrar.jwtLogin(aPayload);
        Cookie cookie = new Cookie("token", response.getResult());
        aHttpServletResponse.addCookie(cookie);
        return response;
    }

    @PostMapping(value = "/registrationNewUser")
    public String handleUserRegistration(DraftUserDTO aDraftUserDTO, Model aModel) {
        bookshopUserRegistrar.registerNewUser(aDraftUserDTO);
        aModel.addAttribute("regOk", true);
        return "signin";
    }

    @PostMapping(value = "/updateCurrentUser")
    public String handleUpdateCurrentUser(DraftUserDTO aDraftUserDTO, Model aModel) throws UserNotFountException {
        if ((aDraftUserDTO.getPassword() == null) || (aDraftUserDTO.getPassword().equals(aDraftUserDTO.getPasswordReply()))) {
            aModel.addAttribute("updateOk", true);
            bookshopUserRegistrar.updateCurrentUser(aDraftUserDTO);
        } else {
            aModel.addAttribute("updateError", false);
        }
        return "profile";
    }

}
