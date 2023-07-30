package com.example.bookshop.exceptions;

public class TransactionException extends  BookshopException {

    private static final long serialVersionUID = 8269995723944142768L;

    public TransactionException(String aMessage) {
        super(aMessage);
    }

    public TransactionException(String aFormat, Object... aArgs) {
        super(String.format(aFormat, aArgs));
    }

}
