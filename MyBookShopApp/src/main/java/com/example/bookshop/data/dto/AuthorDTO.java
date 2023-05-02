package com.example.bookshop.data.dto;

import com.example.bookshop.data.entities.AuthorEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@JsonPropertyOrder(value = {"id", "name", "slug"})
public class AuthorDTO {

    @JsonProperty(value = "id")
    private Long id;

    @JsonIgnore
    private String firstname;

    @JsonIgnore
    private String lastname;

    @JsonIgnore
    private String description;

    @JsonIgnore
    private String photo;

    @JsonProperty(value = "slug")
    private String slug;

    public static AuthorDTO of(AuthorEntity aEntity) {
        return AuthorDTO.builder()
                .id(aEntity.getId())
                .firstname(aEntity.getFirstname())
                .lastname(aEntity.getLastname())
                .slug(aEntity.getSlug())
                .photo(aEntity.getPhoto())
                .description(aEntity.getDescription())
                .build();
    }

    @JsonProperty(value = "name")
    public String getFullname() {
        return firstname + " " + lastname;
    }

}
