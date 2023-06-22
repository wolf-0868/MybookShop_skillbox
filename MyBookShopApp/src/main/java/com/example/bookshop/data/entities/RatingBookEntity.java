package com.example.bookshop.data.entities;

import com.example.bookshop.data.entities.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "rating_book")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class RatingBookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "rate_book_fk"), nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private BookEntity book;

    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "rate_user_fk"), nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @Column(name = "value", nullable = false)
    private Short value = 0;

}
