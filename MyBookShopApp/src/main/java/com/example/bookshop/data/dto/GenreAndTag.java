package com.example.bookshop.data.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GenreAndTag {

    private String tag;

    private GenreDTO value;

}
