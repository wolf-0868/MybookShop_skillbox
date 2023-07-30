package com.example.bookshop.data.entities;

import com.example.bookshop.data.entities.book.links.Book2AuthorEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Table(name = "author")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class AuthorEntity implements Serializable {

    private static final long serialVersionUID = 7062599213133653316L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Book2AuthorEntity> books = new HashSet<>();

    @Column(name = "photo")
    private String photo;

    @Column(name = "slug")
    private String slug;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

}
