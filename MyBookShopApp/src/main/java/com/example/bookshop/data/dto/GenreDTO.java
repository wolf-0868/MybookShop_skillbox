package com.example.bookshop.data.dto;

import com.example.bookshop.data.entities.genre.GenreEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@JsonPropertyOrder(value = {"id", "slug", "name", "parent_id"})
public class GenreDTO {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "slug")
    private String slug;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "parent_id")
    private Long parentId;

    @JsonIgnore
    private int countBooks;

    public static GenreDTO of(GenreEntity aEntity) {
        return GenreDTO.builder()
                .id(aEntity.getId())
                .slug(aEntity.getSlug())
                .name(aEntity.getName())
                .parentId(aEntity.getParentId())
                .countBooks(aEntity.getBooks().size())
                .build();
    }

}
