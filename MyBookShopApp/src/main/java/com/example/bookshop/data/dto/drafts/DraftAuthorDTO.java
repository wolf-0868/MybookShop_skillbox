package com.example.bookshop.data.dto.drafts;

import com.example.bookshop.data.entities.AuthorEntity;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DraftAuthorDTO {

    private Long id;
    private String slug;
    private String firstName;
    private String lastName;
    private String image;
    private String description;

    public static DraftAuthorDTO of(AuthorEntity aEntity) {
        return DraftAuthorDTO.builder()
                .id(aEntity.getId())
                .slug(aEntity.getSlug())
                .firstName(aEntity.getFirstname())
                .lastName(aEntity.getLastname())
                .image(aEntity.getPhoto())
                .description(aEntity.getDescription())
                .build();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

}
