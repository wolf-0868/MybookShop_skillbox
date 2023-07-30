package com.example.bookshop.data.entities.other;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "document")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class DocumentEntity implements Serializable {

    private static final long serialVersionUID = -5731833453584602436L;

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
