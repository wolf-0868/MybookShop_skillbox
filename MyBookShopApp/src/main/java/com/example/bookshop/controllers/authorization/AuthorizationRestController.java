package com.example.bookshop.controllers.authorization;

import com.example.bookshop.data.dto.drafts.DraftUserDTO;
import com.example.bookshop.data.payloads.ContactConfirmationPayload;
import com.example.bookshop.data.payloads.LoginPassConfirmationPayload;
import com.example.bookshop.exceptions.BookshopUserRegistrarException;
import com.example.bookshop.exceptions.UserNotFountException;
import com.example.bookshop.security.BookshopUserRegistrar;
import com.example.bookshop.security.ConfirmationResponse;
import com.example.bookshop.security.MessageConfirmationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class AuthorizationRestController {

    private final BookshopUserRegistrar bookshopUserRegistrar;

    @PostMapping(value = "/requestContactConfirmation")
    public ConfirmationResponse handleRequestContactConfirmation(@RequestBody ContactConfirmationPayload aPayload) {
        return new ConfirmationResponse(true);
    }

    @PostMapping(value = "/approveContact")
    public ConfirmationResponse handleApproveContact(@RequestBody ContactConfirmationPayload aPayload) {
        return new ConfirmationResponse(true);
    }

    @PostMapping(value = "/login")
    public ConfirmationResponse handleLogin(@RequestBody LoginPassConfirmationPayload aPayload, HttpServletResponse aHttpServletResponse) {
        ConfirmationResponse response = bookshopUserRegistrar.jwtLogin(aPayload);
        Cookie cookie = new Cookie("token", response.getResult());
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        aHttpServletResponse.addCookie(cookie);
        return response;
    }

    @PostMapping(value = "/registrationNewUser")
    public String handleUserRegistration(DraftUserDTO aDraftUserDTO, Model aModel) {
        bookshopUserRegistrar.registerNewUser(aDraftUserDTO);
        aModel.addAttribute("regOk", true);
        return "signin";
    }

    @PostMapping(value = "/updateUser")
    public String handleUpdateCurrentUser(DraftUserDTO aDraftUserDTO, Model aModel) throws UserNotFountException {
        MessageConfirmationResponse response = new MessageConfirmationResponse(false);
        if (aDraftUserDTO.getPassword() == null) {
            response.setMessage("Empty password");
        } else if (!aDraftUserDTO.getPassword().equals(aDraftUserDTO.getPasswordReply())) {
            response.setMessage("Passwords not matched");
        } else {
            try {
                bookshopUserRegistrar.updateCurrentUser(aDraftUserDTO);
                response.setResult(true);
            } catch (BookshopUserRegistrarException e) {
                response.setResult(false);
                response.setMessage(e.getMessage());
            }
        }
        aModel.addAttribute("updateResponse", response);
        return "profile";
    }

}
