package com.example.bookshop.data.struct.book.links;

import com.example.bookshop.data.BookEntity;
import com.example.bookshop.data.struct.user.UserEntity;
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

    @Column(name = "datetime", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "type_id", nullable = false)
    private Integer typeId;

    @ManyToOne
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "book2user_book_fk"), nullable = false)
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "book2user_user_fk"), nullable = false)
    private UserEntity user;

}
