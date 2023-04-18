package com.example.bookshop.model;

import com.example.bookshop.data.entities.AuthorEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Author {

    private String firstname;

    private String lastname;

    public static Author of(AuthorEntity aEntity) {
        return Author.builder()
                .firstname(aEntity.getFirstname())
                .lastname(aEntity.getLastname())
                .build();
    }

    public String getFullname() {
        return firstname + " " + lastname;
    }

}
