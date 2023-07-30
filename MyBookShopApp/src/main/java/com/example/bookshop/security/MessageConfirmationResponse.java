package com.example.bookshop.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageConfirmationResponse extends ConfirmationResponse {

    private String message;

    public MessageConfirmationResponse(boolean aResult) {
        this(aResult, null);
    }

    public MessageConfirmationResponse(boolean aResult, String aMessage) {
        super(aResult);
        message = aMessage;
    }

}
