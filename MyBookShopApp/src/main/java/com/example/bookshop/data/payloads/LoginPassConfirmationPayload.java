package com.example.bookshop.data.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginPassConfirmationPayload {

    private String login;
    private String password;

}
