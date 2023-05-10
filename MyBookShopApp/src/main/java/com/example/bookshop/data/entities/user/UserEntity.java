package com.example.bookshop.data.entities.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "hash", nullable = false)
    private String hash = "0";

    @Column(name = "reg_datetime", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime regDatetime = LocalDateTime.now();

    @Column(name = "balance", nullable = false)
    private Integer balance = 0;

}
