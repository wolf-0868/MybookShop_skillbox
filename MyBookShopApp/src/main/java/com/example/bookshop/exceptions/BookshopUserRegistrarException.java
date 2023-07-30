package com.example.bookshop.exceptions;

public class BookshopUserRegistrarException extends BookshopException {

    private static final long serialVersionUID = -4725725184417629428L;

    public BookshopUserRegistrarException(String aMessage) {
        super(aMessage);
    }

    public BookshopUserRegistrarException(String aFormat, Object... aArgs) {
        super(String.format(aFormat, aArgs));
    }

}
