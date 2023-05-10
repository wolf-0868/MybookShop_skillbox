package com.example.bookshop.controllers;

import com.example.bookshop.security.*;
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
    public AuthUserController(BookshopUserRegistrar aBookshopUserRegistrer) {
        bookshopUserRegistrar = aBookshopUserRegistrer;
    }

    @GetMapping(value = "/signin")
    public String signInPage() {
        return "signin";
    }

    @GetMapping(value = "/signup")
    public String signUpPage(Model aModel) {
        aModel.addAttribute("regForm", new RegistrationForm());
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

    @PostMapping(value = "/reg")
    public String handleUserRegistration(RegistrationForm aRegistrationForm, Model aModel) {
        bookshopUserRegistrar.registerNewUser(aRegistrationForm);
        aModel.addAttribute("regOk", true);
        return "signin";
    }

    @ResponseBody
    @PostMapping(value = "/login")
    public ConfirmationResponse handleLogin(@RequestBody LoginPassConfirmationPayload aPayload, HttpServletResponse aHttpServletResponse) {
        ConfirmationResponse response = bookshopUserRegistrar.jwtLogin(aPayload);
        Cookie cookie = new Cookie("token", response.getResult());
        aHttpServletResponse.addCookie(cookie);
        return response;
    }

//    @GetMapping(value = "/logout")
//    public String handleLogout(HttpServletRequest aRequest) {
//        HttpSession session = aRequest.getSession();
//        SecurityContextHolder.clearContext();
//
//        if (session != null) {
//            session.invalidate();
//        }
//        for (Cookie cookie : aRequest.getCookies()) {
//            cookie.setMaxAge(0);
//        }
//        return "redirect:/";
//    }

    @GetMapping(value = "/my")
    public String handleMy() {
        return "my";
    }

    @GetMapping(value = "/profile")
    public String handleProfile(Model aModel) {
        aModel.addAttribute("currentUserDTO", bookshopUserRegistrar.getCurrentUser());
        return "profile";
    }

}
