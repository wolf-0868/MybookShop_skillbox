package com.example.bookshop.data.entities;

import com.example.bookshop.data.entities.enums.JournalEntryType;
import com.example.bookshop.data.entities.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "journal")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class JournalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private JournalEntryType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "journal_user_fk"))
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "journal_book_fk"))
    private BookEntity book;

    @Column(name = "datetime", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime dateTime = LocalDateTime.now();

}
