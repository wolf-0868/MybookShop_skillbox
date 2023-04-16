package com.example.bookshop.data.struct.book.review;

import com.example.bookshop.data.struct.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "book_review_like")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookReviewLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id", foreignKey = @ForeignKey(name = "book_review_like_review_fk"), nullable = false)
    private BookReviewEntity review;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "book_review_like_user_fk"), nullable = false)
    private UserEntity user;

    @Column(name = "datetime", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
    private LocalDateTime time;

    @Column(name = "value", columnDefinition = "SMALLINT", nullable = false)
    private Short value;


}
