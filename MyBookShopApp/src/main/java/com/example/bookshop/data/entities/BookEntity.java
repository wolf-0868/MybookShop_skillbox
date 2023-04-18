package com.example.bookshop.data.entities;

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

    public static final String RUB_SIMBOL = "\u0584";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "book2author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<AuthorEntity> authors = new HashSet<>();

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

}
