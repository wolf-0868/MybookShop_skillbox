package com.example.bookshop.model;

import com.example.bookshop.data.entities.BookEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter
@ToString
public class Book {

    public static final String RUB_SYMBOL = "\u0584";

    private String title;

    private Set<Author> authors;

    private LocalDate pubDate;

    private boolean bestseller;

    private int price;

    private int discount = 0;

    public static Book of(BookEntity aEntity) {
        return Book.builder()
                .title(aEntity.getTitle())
                .authors(aEntity.getAuthors()
                        .stream()
                        .map(Author::of)
                        .collect(Collectors.toSet()))
                .pubDate(aEntity.getPubDate())
                .bestseller(aEntity.isBestseller())
                .price(aEntity.getPrice())
                .discount(aEntity.getDiscount())
                .build();
    }

    private int getCurrentPrice() {
        return price - (int) (((double) price) / 100 * discount);
    }

    public String getPriceText() {
        return RUB_SYMBOL + price;
    }

    public String getCurrentPriceText() {
        return RUB_SYMBOL + getCurrentPrice();
    }

    public boolean isBigDiscount() {
        return discount >= 30;
    }

    public String getAuthorsText() {
        return authors.stream()
                .map(Author::getFullname)
                .collect(Collectors.joining(", "));
    }

}
