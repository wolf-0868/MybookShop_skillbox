package com.example.bookshop.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "book_file")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hash")
    private String hash;

    @Column(name = "type_id")
    private Long _typeId;

    @Column(name = "path")
    private String path;

}
