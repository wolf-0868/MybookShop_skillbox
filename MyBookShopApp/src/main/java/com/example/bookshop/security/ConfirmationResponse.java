package com.example.bookshop.security;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConfirmationResponse {

    protected String result;

    public ConfirmationResponse(String aResult) {
        result = aResult;
    }

    public ConfirmationResponse(boolean aResult) {
        this(Boolean.toString(aResult));
    }

    public void setResult(String aResult) {
        result = aResult;
    }

    public void setResult(boolean aResult) {
        result = Boolean.toString(aResult);
    }

}
