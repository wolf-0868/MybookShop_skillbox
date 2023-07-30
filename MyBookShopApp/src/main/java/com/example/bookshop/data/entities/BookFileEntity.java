package com.example.bookshop.data.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "book_file")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookFileEntity implements Serializable {

    private static final long serialVersionUID = 1457996128623119983L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hash", nullable = false)
    private String hash;

    @Column(name = "type_id", nullable = false)
    private Long typeId;

    @Column(name = "path", nullable = false)
    private String path;

}
