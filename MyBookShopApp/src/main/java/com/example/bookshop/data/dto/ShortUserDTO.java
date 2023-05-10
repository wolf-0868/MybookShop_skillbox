package com.example.bookshop.data.dto;

import com.example.bookshop.data.entities.user.UserEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ShortUserDTO {

    protected Long id;

    protected String name;

    protected Integer balance;

    public static ShortUserDTO of(UserEntity aEntity) {
        return ShortUserDTO.builder()
                .id(aEntity.getId())
                .name(aEntity.getName())
                .balance(aEntity.getBalance())
                .build();
    }

}
