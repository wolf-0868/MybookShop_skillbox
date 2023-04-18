package com.example.bookshop.data.entities.other;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "document")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sort_index", nullable = false)
    private Integer sortIndex = 0;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "text", columnDefinition = "TEXT", nullable = false)
    private String text;

}
