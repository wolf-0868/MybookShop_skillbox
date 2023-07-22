package com.example.bookshop.data.dto.page;

import com.example.bookshop.data.dto.BookDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BooksPageDTO {

    @JsonProperty(value = "count")
    private Integer count;

    @JsonProperty(value = "books")
    private List<BookDTO> books;

    public BooksPageDTO(List<BookDTO> aBooks) {
        books = aBooks;
        count = aBooks.size();
    }

    public int getSumPrice() {
        return books.stream()
                .mapToInt(BookDTO::getPrice)
                .sum();
    }

    public int getSumDiscountPrice() {
        return books.stream()
                .mapToInt(BookDTO::getDiscountPrice)
                .sum();
    }

    public boolean isEnableDiscount() {
        return books.stream()
                .noneMatch(b -> b.getDiscount() == 0);
    }

    public List<Long> getBookIds() {
        return books.stream()
                .map(BookDTO::getId)
                .collect(Collectors.toList());
    }

}
