package com.example.bookshop.controllers.profile;

import com.example.bookshop.data.dto.page.TransactionsPageDTO;
import com.example.bookshop.data.payloads.PaymentBalancePayload;
import com.example.bookshop.exceptions.TransactionException;
import com.example.bookshop.exceptions.UserNotFountException;
import com.example.bookshop.security.BookshopUserRegistrar;
import com.example.bookshop.security.ConfirmationResponse;
import com.example.bookshop.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ProfilePageController {

    private final BookshopUserRegistrar bookshopUserRegistrar;
    private final TransactionService transactionService;

    @GetMapping(value = "profile")
    public String getProfilePage(Model aModel) throws UserNotFountException {
        aModel.addAttribute("draftUserDTO", bookshopUserRegistrar.getDraftCurrentUser());
        aModel.addAttribute("transactions", new TransactionsPageDTO(transactionService.getPageTransactions(bookshopUserRegistrar.getCurrentIdUser(), 0, 4)));
        return "profile";
    }

    @ResponseBody
    @PostMapping(value = "payment")
    public ConfirmationResponse handlePaymentBalance(@RequestBody PaymentBalancePayload aPayload) throws UserNotFountException, TransactionException {
        transactionService.transactionBalance(bookshopUserRegistrar.getCurrentIdUser(), aPayload.getSum());
        ConfirmationResponse response = new ConfirmationResponse();
        response.setResult("true");
        return response;
    }

    @ResponseBody
    @GetMapping(value = "transactions", params = {"offset", "limit"})
    public TransactionsPageDTO getTransactions(@RequestParam("offset") int aOffset, @RequestParam("limit") int aLimit) throws UserNotFountException {
        return new TransactionsPageDTO(transactionService.getPageTransactions(bookshopUserRegistrar.getCurrentIdUser(), aOffset, aLimit));
    }

}
