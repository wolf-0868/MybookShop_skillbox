package com.example.bookshop.data.entities.book.review;

import com.example.bookshop.data.entities.BookEntity;
import com.example.bookshop.data.entities.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "book_review")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookReviewEntity implements Serializable {

    private static final long serialVersionUID = -7278612065105131572L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "book_review_book_fk"), nullable = false)
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_review_user_fk"), nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BookReviewLikeEntity> likes = new HashSet<>();

    @Column(name = "datetime", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime datetime = LocalDateTime.now();

    @Column(name = "text", columnDefinition = "TEXT", nullable = false)
    private String text;

}
