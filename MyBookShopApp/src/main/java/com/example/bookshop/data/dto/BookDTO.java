package com.example.bookshop.data.dto;

import com.example.bookshop.data.entities.BookEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.stream.Collectors;

@SuperBuilder
@Getter
@ToString
@JsonPropertyOrder(value = {"id", "slug", "title", "image", "authors", "discount", "isBestseller", "rating", "status", "price", "discountPrice"})
public class BookDTO extends AbstractBookEntry {

    @JsonProperty("rating")
    private double rating;

    @JsonIgnore
    private double popularity;

    @JsonIgnore
    private LocalDate pubDate;

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
                .popularity(aEntity.getPopularity())
                .price(aEntity.getPrice())
                .discount(aEntity.getDiscount())
                .build();
    }

}
