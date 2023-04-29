package com.example.bookshop.controllers.data.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "book_file")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hash", nullable = false)
    private String hash;

    @Column(name = "type_id", nullable = false)
    private Long _typeId;

    @Column(name = "path", nullable = false)
    private String path;

}
