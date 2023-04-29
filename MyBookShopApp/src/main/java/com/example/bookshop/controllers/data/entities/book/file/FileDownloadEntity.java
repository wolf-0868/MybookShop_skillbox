package com.example.bookshop.controllers.data.entities.book.file;

import com.example.bookshop.controllers.data.entities.BookEntity;
import com.example.bookshop.controllers.data.entities.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "file_download")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class FileDownloadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "file_download_book_fk"), nullable = false)
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "file_download_user_fk"), nullable = false)
    private UserEntity user;

    @Column(name = "count", nullable = false)
    private Integer count = 1;

}
