package com.example.bookshop.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@Accessors(prefix = "_")
public class Book {

    public static final String RUB_SIMBOL = "\u0584";

    private Integer _id;
    private Author _author;
    private LocalDate _pubDate;
    private boolean _bestseller;
    private String _title;
    private Integer _price;
    private Integer _discount;

    public Integer getCurrentPrice() {
        return _price - (int) (((double) _price) / 100 * _discount);
    }

    public String getPriceText() {
        return RUB_SIMBOL + _price;
    }

    public String getCurrentPriceText() {
        return RUB_SIMBOL + getCurrentPrice();
    }

    public boolean isBigDiscount() {
        return _discount >= 30;
    }

}
