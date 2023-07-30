package com.example.bookshop.data.entities.book.file;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "book_file_type")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookFileTypeEntity implements Serializable {

    private static final long serialVersionUID = 1083362136071488339L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

}
