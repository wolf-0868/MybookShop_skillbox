package com.example.bookshop.data.entities.book.links;

import com.example.bookshop.data.entities.AuthorEntity;
import com.example.bookshop.data.entities.BookEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "book2author")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book2AuthorEntity implements Serializable {

    private static final long serialVersionUID = -2158245112962375458L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @MapsId(value = "book_id")
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "book2author_book_fk"), nullable = false)
    private BookEntity book;

    @ManyToOne
    @MapsId(value = "author_id")
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "book2author_author_fk"), nullable = false)
    private AuthorEntity author;

    @Column(name = "sort_index", nullable = false)
    private Integer sortIndex = 0;

}
