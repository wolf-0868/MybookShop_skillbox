package com.example.bookshop.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@SuperBuilder
@Getter
@Setter
public abstract class AbstractBookEntry {

    @JsonProperty(value = "id")
    protected Long id;

    @JsonProperty(value = "slug")
    protected String slug;

    @JsonProperty(value = "title")
    protected String title;

    @JsonProperty(value = "image")
    protected String image;

    @JsonProperty(value = "discount")
    protected int discount = 0;

    @JsonProperty(value = "isBestseller")
    protected boolean bestseller;

    @JsonProperty(value = "price")
    protected int price;

    @JsonIgnore
    @Builder.Default
    protected Set<AuthorDTO> authors = new HashSet<>();

    @JsonProperty(value = "discountPrice")
    public int getDiscountPrice() {
        return price - (int) (((double) price) / 100 * discount);
    }

    @JsonProperty(value = "authors")
    public String getAuthorsText() {
        return authors.stream()
                .map(AuthorDTO::getFullname)
                .collect(Collectors.joining(", "));
    }

    @JsonIgnore
    public boolean isDiscount() {
        return discount > 0;
    }

    @JsonIgnore
    public boolean isBigDiscount() {
        return discount >= 30;
    }

}
