package com.example.bookshop.exceptions;

public class TransactionException extends  BookshopException {

    public TransactionException(String aMessage) {
        super(aMessage);
    }

    public TransactionException(String aFormat, Object... aArgs) {
        super(String.format(aFormat, aArgs));
    }

}
