package com.example.bookshop.controllers.data.entities.genre;

import com.example.bookshop.controllers.data.entities.BookEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "genre")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class GenreEntity {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book2genre",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<BookEntity> books = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "parent_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "genre_parent_fk"))
    private Set<GenreEntity> children = new HashSet<>();

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "name", nullable = false)
    private String name;


}
