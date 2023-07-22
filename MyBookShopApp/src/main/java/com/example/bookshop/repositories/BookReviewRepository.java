package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.BookEntity;
import com.example.bookshop.data.entities.book.review.BookReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReviewEntity, Long> {

    List<BookReviewEntity> findByBookId(long aBookId);

    @Modifying
    @Transactional
    void deleteAllByBook(BookEntity aBook);

}
