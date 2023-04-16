package com.example.bookshop.data.struct.book.links;

import com.example.bookshop.data.BookEntity;
import com.example.bookshop.data.struct.genre.GenreEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "book2genre")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book2GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "book2genre_book_fk"), nullable = false)
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "genre_id", foreignKey = @ForeignKey(name = "book2genre_genre_fk"), nullable = false)
    private GenreEntity genre;

}
