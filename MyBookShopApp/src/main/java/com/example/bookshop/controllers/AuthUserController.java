package com.example.bookshop.controllers;

import com.example.bookshop.data.dto.drafts.DraftUserDTO;
import com.example.bookshop.data.payloads.ContactConfirmationPayload;
import com.example.bookshop.data.payloads.LoginPassConfirmationPayload;
import com.example.bookshop.exceptions.BookshopUserRegistrarException;
import com.example.bookshop.exceptions.UserNotFountException;
import com.example.bookshop.security.BookshopUserRegistrar;
import com.example.bookshop.security.ConfirmationResponse;
import com.example.bookshop.security.MessageConfirmationResponse;
import com.example.bookshop.security.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class AuthUserController {

    private final BookshopUserRegistrar bookshopUserRegistrar;
    private final SmsService smsService;
//    private final JavaMailSender javaMailSender;

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
//        if (!aPayload.getContact().contains("@")) {
//            String smsCodeString = smsService.sendSecretCodeSms(aPayload.getContact());
//            smsService.saveNewCode(new SmsCodeEntity(smsCodeString, 60)); // expires in 1 min
//        }
        response.setResult("true");
        return response;
    }

//    @ResponseBody
//    @PostMapping(value = "/requestEmailContactConfirmation")
//    public ConfirmationResponse handleRequestEmailContactConfirmation(@RequestBody ContactConfirmationPayload aPayload) {
//        ConfirmationResponse response = new ConfirmationResponse();
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("bookshop123123@mail.ru");
//        message.setTo(aPayload.getContact());
//        SmsCodeEntity smsCode = new SmsCodeEntity(smsService.generateCode(), 300); // 5 minutes
//        smsService.saveNewCode(smsCode);
//        message.setSubject("Bookshop");
//        message.setText("Verification code is: " + smsCode.getCode());
//        javaMailSender.send(message);
//        response.setResult("true");
//        return response;
//    }

    @ResponseBody
    @PostMapping(value = "/approveContact")
    public ConfirmationResponse handleApproveContact(@RequestBody ContactConfirmationPayload aPayload) {
        ConfirmationResponse response = new ConfirmationResponse();
//        if (smsService.verifyCode(aPayload.getCode())) {
//            response.setResult("true");
//            return response;
//        } else {
//            if (aPayload.getContact().contains("@")) {
//                response.setResult("true");
//                return response;
//            } else {
//                return new ConfirmationResponse();
//            }
//        }
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

//    @ResponseBody
//    @PostMapping(value = "/login-by-phone-number")
//    public ConfirmationResponse handlePhoneNumber(@RequestBody ContactConfirmationPayload aPayload, HttpServletResponse aHttpServletResponse) {
//        if (smsService.verifyCode(aPayload.getCode())) {
//            ConfirmationResponse response = bookshopUserRegistrar.jwtLoginByPhoneNumber(aPayload);
//            Cookie cookie = new Cookie("token", response.getResult());
//            aHttpServletResponse.addCookie(cookie);
//            return response;
//        } else {
//            return null;
//        }
//    }

    @PostMapping(value = "/registrationNewUser")
    public String handleUserRegistration(DraftUserDTO aDraftUserDTO, Model aModel) {
        bookshopUserRegistrar.registerNewUser(aDraftUserDTO);
        aModel.addAttribute("regOk", true);
        return "signin";
    }

    @PostMapping(value = "/updateUser")
    public String handleUpdateCurrentUser(DraftUserDTO aDraftUserDTO, Model aModel) throws UserNotFountException {
        MessageConfirmationResponse response = new MessageConfirmationResponse();
        response.setResult("false");
        if (aDraftUserDTO.getPassword() == null) {
            response.setMessage("Empty password");
        } else if (!aDraftUserDTO.getPassword().equals(aDraftUserDTO.getPasswordReply())) {
            response.setMessage("Passwords not matched");
        } else {
            try {
                bookshopUserRegistrar.updateCurrentUser(aDraftUserDTO);
                response.setResult("true");
            } catch (BookshopUserRegistrarException e) {
                response.setResult("false");
                response.setMessage(e.getMessage());
            }
        }
        aModel.addAttribute("updateResponse", response);
        return "profile";
    }

}
