package com.example.bookshop.data.entities.payments;

import com.example.bookshop.data.entities.BookEntity;
import com.example.bookshop.data.entities.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "balance_transaction")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransactionEntity implements Serializable {

    private static final long serialVersionUID = -1188686095710351562L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "balance_transaction_user_fk"), nullable = false)
    private UserEntity user;

    @Column(name = "datetime", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime datetime;

    @Column(name = "value", nullable = false)
    private Integer value = 0;

    @ManyToOne
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "balance_transaction_book_fk"))
    private BookEntity book;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

}
