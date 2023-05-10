package com.example.bookshop.data.dto;

import com.example.bookshop.data.entities.user.UserEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class UserDTO extends ShortUserDTO {

    private String email;

    private String phone;

    public static UserDTO of(UserEntity aEntity) {
        return UserDTO.builder()
                .id(aEntity.getId())
                .name(aEntity.getName())
                .email(aEntity.getEmail())
                .phone(aEntity.getPhone())
                .balance(aEntity.getBalance())
                .build();
    }

}
