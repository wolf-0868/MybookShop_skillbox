package com.example.bookshop.controllers.authorization;

import com.example.bookshop.data.dto.drafts.DraftUserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorizationPageController {

    @GetMapping(value = "/signin")
    public String signInPage() {
        return "signin";
    }

    @GetMapping(value = "/signup")
    public String signUpPage(Model aModel) {
        aModel.addAttribute("draftUserDTO", new DraftUserDTO());
        return "signup";
    }

}
