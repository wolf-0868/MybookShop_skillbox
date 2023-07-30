package com.example.bookshop.data.entities.book.review;

import com.example.bookshop.data.entities.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "message")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class MessageEntity implements Serializable {

    private static final long serialVersionUID = 6843484531746532424L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "datetime", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime datetime = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "message_user_fk"))
    private UserEntity user;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "text", columnDefinition = "TEXT", nullable = false)
    private String text;

}
