package com.example.bookshop.data.entities;

import com.example.bookshop.data.entities.book.links.Book2AuthorEntity;
import com.example.bookshop.data.entities.book.links.Book2GenreEntity;
import com.example.bookshop.data.entities.book.links.Book2UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Table(name = "book")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Book2AuthorEntity> authors = new HashSet<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Book2GenreEntity> genres = new HashSet<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Book2UserEntity> bindingUsers = new HashSet<>();

    @Column(name = "pub_date", columnDefinition = "DATE", nullable = false)
    private LocalDate pubDate;

    @Column(name = "is_bestseller", nullable = false)
    private boolean bestseller;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "discount", nullable = false)
    private Integer discount = 0;

    @Column(name = "rating")
    private double rating = 0;

    @Column(name = "popularity")
    private double popularity = 0;

}
