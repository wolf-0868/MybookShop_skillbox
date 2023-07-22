package com.example.bookshop.data.entities.book.links;

import com.example.bookshop.data.entities.BookEntity;
import com.example.bookshop.data.entities.genre.GenreEntity;
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
    @MapsId(value = "book_id")
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "book2genre_book_fk"), nullable = false)
    private BookEntity book;

    @ManyToOne
    @MapsId(value = "genre_id")
    @JoinColumn(name = "genre_id", foreignKey = @ForeignKey(name = "book2genre_genre_fk"), nullable = false)
    private GenreEntity genre;

}
