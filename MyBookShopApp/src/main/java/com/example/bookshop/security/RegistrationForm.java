package com.example.bookshop.security;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationForm {

    private String name;

    private String email;

    private String phone;

    private String password;

}
