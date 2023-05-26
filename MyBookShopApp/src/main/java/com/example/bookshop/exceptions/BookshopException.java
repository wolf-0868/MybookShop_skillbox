package com.example.bookshop.exceptions;

public class BookshopException extends Exception {

    public BookshopException() {
        super();
    }

    public BookshopException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }

    public BookshopException(String aMessage) {
        super(aMessage);
    }

    public BookshopException(String aFormat, Object... aArgs) {
        super(String.format(aFormat, aArgs));
    }

    public BookshopException(Throwable aCause) {
        super(aCause);
    }

}
