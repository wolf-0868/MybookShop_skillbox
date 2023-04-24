package com.example.bookshop.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class BookFilter {

    private String title;

    private Boolean isBestseller;

    private LocalDate fromDate;

    private LocalDate toDate;

}
