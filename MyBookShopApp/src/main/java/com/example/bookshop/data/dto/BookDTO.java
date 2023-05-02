package com.example.bookshop.data.dto;

import com.example.bookshop.data.entities.BookEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter
@ToString
@JsonPropertyOrder(value = {"id", "slug", "title", "image", "authors", "discount", "isBestseller", "rating", "status", "price", "discountPrice"})
public class BookDTO {

    public static final String RUB_SYMBOL = "\u0584";

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "slug")
    private String slug;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "image")
    private String image;

    @JsonProperty(value = "discount")
    private int discount = 0;

    @JsonProperty(value = "isBestseller")
    private boolean bestseller;

    @JsonProperty(value = "rating")
    private double rating;

    @JsonProperty(value = "price")
    private int price;

    @JsonIgnore
    private LocalDate pubDate;

    @JsonIgnore
    @Builder.Default
    private Set<AuthorDTO> authors = new HashSet<>();

    public static BookDTO of(BookEntity aEntity) {
        return BookDTO.builder()
                .id(aEntity.getId())
                .slug(aEntity.getSlug())
                .title(aEntity.getTitle())
                .image(aEntity.getImage())
                .authors(aEntity.getAuthors()
                        .stream()
                        .map(AuthorDTO::of)
                        .collect(Collectors.toSet()))
                .pubDate(aEntity.getPubDate())
                .bestseller(aEntity.isBestseller())
                .rating(aEntity.getRating())
                .price(aEntity.getPrice())
                .discount(aEntity.getDiscount())
                .build();
    }

    @JsonProperty(value = "discountPrice")
    private int getDiscountPrice() {
        return price - (int) (((double) price) / 100 * discount);
    }

    @JsonProperty(value = "authors")
    public String getAuthorsText() {
        return authors.stream()
                .map(AuthorDTO::getFullname)
                .collect(Collectors.joining(", "));
    }

    @JsonIgnore
    public String getPriceText() {
        return RUB_SYMBOL + price;
    }

    @JsonIgnore
    public String getDiscountPriceText() {
        return RUB_SYMBOL + getDiscountPrice();
    }

    @JsonIgnore
    public boolean isBigDiscount() {
        return discount >= 30;
    }

}
