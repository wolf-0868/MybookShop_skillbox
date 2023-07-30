package com.example.bookshop.data.entities.book.links;

import com.example.bookshop.data.entities.BookEntity;
import com.example.bookshop.data.entities.genre.GenreEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "book2genre")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book2GenreEntity implements Serializable {

    private static final long serialVersionUID = -4618336087840100157L;

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
