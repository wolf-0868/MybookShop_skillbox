package com.example.bookshop.data.entities.genre;

import com.example.bookshop.data.entities.book.links.Book2GenreEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Table(name = "genre")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class GenreEntity implements Serializable {

    private static final long serialVersionUID = -8504329118918170411L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY)
    private Set<Book2GenreEntity> books = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
