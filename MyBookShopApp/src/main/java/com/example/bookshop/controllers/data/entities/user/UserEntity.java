package com.example.bookshop.controllers.data.entities.user;

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

    @Column(name = "hash", nullable = false)
    private String hash;

    @Column(name = "reg_datetime", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
    private LocalDateTime regDatetime;

    @Column(name = "balance", nullable = false)
    private Integer balance;

    @Column(name = "name", nullable = false)
    private String name;

}
