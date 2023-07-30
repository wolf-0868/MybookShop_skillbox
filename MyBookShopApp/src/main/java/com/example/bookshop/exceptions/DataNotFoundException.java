package com.example.bookshop.exceptions;

import java.util.Map;

public class DataNotFoundException extends BookshopException {

    private static final long serialVersionUID = -146070290329641472L;

    public DataNotFoundException(String aMessage) {
        super(aMessage);
    }

    public DataNotFoundException() {
        super("Не удалось найти данные");
    }

    public DataNotFoundException(Map<String, ?> aMaps, String aDataClass) {
        super(String.format("Не удалось найти данные класса %s по следующим параметрам: %s", aDataClass, convertWithIteration(aMaps)));
    }

     private static String convertWithIteration(Map<String, ?> aMaps) {
        StringBuilder mapAsString = new StringBuilder("{");
        for (Map.Entry<String, ?> entry : aMaps.entrySet()) {
            mapAsString.append(entry.getKey() + "=" + entry.getValue() + ", ");
        }
        mapAsString.delete(mapAsString.length() - 2, mapAsString.length()).append("}");
        return mapAsString.toString();
    }

}
