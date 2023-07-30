package com.example.bookshop.exceptions;

public class UserNotFountException extends BookshopException {

    private static final long serialVersionUID = 6519397706388933403L;

    public UserNotFountException(String aMessage) {
        super(aMessage);
    }

    public UserNotFountException(String aFormat, Object... aArgs) {
        super(String.format(aFormat, aArgs));
    }

}
