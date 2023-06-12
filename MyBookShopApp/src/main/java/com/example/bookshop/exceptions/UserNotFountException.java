package com.example.bookshop.exceptions;

public class UserNotFountException extends BookshopException {

    public UserNotFountException(String aMessage) {
        super(aMessage);
    }

    public UserNotFountException(String aFormat, Object... aArgs) {
        super(String.format(aFormat, aArgs));
    }

}
