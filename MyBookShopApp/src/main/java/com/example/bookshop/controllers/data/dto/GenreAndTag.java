package com.example.bookshop.controllers.data.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GenreAndTag {

    private String tag;

    private GenreDTO value;

}
