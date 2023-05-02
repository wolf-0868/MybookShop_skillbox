package com.example.bookshop.data.entities.book.links;

import com.example.bookshop.data.entities.BookEntity;
import com.example.bookshop.data.entities.enums.BookBindingType;
import com.example.bookshop.data.entities.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "book2user")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book2UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "datetime", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime dateTime = LocalDateTime.now();

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookBindingType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "book2user_book_fk"), nullable = false)
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "book2user_user_fk"), nullable = false)
    private UserEntity user;

}
