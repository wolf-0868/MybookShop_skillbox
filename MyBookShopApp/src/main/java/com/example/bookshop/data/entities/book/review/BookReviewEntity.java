package com.example.bookshop.data.entities.book.review;

import com.example.bookshop.data.entities.BookEntity;
import com.example.bookshop.data.entities.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "book_review")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "book_review_book_fk"), nullable = false)
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_review_user_fk"), nullable = false)
    private UserEntity user;

    @Column(name = "datetime", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
    private LocalDateTime datetime;

    @Column(name = "text", columnDefinition = "TEXT", nullable = false)
    private String text;

}
