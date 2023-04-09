package com.example.bookshop.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Builder
@Getter
@Setter
@ToString
@Accessors(prefix = "_")
public class Author {

    private Integer _id;
    private String _fullname;

}
