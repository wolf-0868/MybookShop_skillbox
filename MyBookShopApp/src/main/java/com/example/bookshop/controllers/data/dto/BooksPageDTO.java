package com.example.bookshop.controllers.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

}
