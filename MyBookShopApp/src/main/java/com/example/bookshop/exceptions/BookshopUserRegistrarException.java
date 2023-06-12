package com.example.bookshop.exceptions;

public class BookshopUserRegistrarException extends BookshopException {

    public BookshopUserRegistrarException(String aMessage) {
        super(aMessage);
    }

    public BookshopUserRegistrarException(String aFormat, Object... aArgs) {
        super(String.format(aFormat, aArgs));
    }

}
