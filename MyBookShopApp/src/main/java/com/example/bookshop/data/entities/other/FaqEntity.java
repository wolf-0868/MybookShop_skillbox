package com.example.bookshop.data.entities.other;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "faq")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class FaqEntity {

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
