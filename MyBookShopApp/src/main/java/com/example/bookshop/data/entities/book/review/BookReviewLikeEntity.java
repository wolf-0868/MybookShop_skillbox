package com.example.bookshop.data.entities.book.review;

import com.example.bookshop.data.entities.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "book_review_like")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookReviewLikeEntity implements Serializable {

    private static final long serialVersionUID = -5730802696081853703L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", foreignKey = @ForeignKey(name = "book_review_like_review_fk"), nullable = false)
    private BookReviewEntity review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "book_review_like_user_fk"), nullable = false)
    private UserEntity user;

    @Column(name = "datetime", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime datetime = LocalDateTime.now();

    @Column(name = "value", columnDefinition = "SMALLINT", nullable = false)
    private Short value;


}
