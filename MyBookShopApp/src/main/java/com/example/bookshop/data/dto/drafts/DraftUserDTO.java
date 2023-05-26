package com.example.bookshop.data.dto.drafts;

import com.example.bookshop.data.entities.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonPropertyOrder(value = {"name", "email", "phone", "password", "passwordReply"})
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DraftUserDTO {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "phone")
    private String phone;

    @JsonProperty(value = "password")
    private String password;

    @JsonProperty(value = "passwordReply")
    private String passwordReply;

    public static DraftUserDTO of(UserEntity aEntity) {
        return DraftUserDTO.builder()
                .name(aEntity.getName())
                .email(aEntity.getEmail())
                .phone(aEntity.getPhone())
                .build();
    }

}
