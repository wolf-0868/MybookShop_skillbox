package com.example.bookshop.data.dto.drafts;

import com.example.bookshop.data.entities.AuthorEntity;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DraftAuthorBookDTO {

    private Long id;
    private String fullName;

    public static DraftAuthorBookDTO of(AuthorEntity aEntity) {
        return DraftAuthorBookDTO.builder()
                .id(aEntity.getId())
                .fullName(aEntity.getFirstname() + " " + aEntity.getLastname())
                .build();
    }

}
