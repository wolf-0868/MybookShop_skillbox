package com.example.bookshop.data.entities.other;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "faq")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class FaqEntity implements Serializable {

    private static final long serialVersionUID = -7013092056946558970L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sort_index", nullable = false)
    private Integer sortIndex = 0;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "answer", columnDefinition = "TEXT", nullable = false)
    private String answer;

}
