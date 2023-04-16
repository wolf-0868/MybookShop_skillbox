package com.example.bookshop.data.struct.book.links;

import com.example.bookshop.data.AuthorEntity;
import com.example.bookshop.data.BookEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "book2author")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book2AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "book2author_book_fk"), nullable = false)
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "book2author_author_fk"), nullable = false)
    private AuthorEntity author;

    @Column(name = "sort_index", nullable = false)
    private Integer sortIndex = 0;

}
