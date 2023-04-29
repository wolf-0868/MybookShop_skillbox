package com.example.bookshop.repositories;

import com.example.bookshop.controllers.data.entities.book.review.BookReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReviewEntity, Long> {

    List<BookReviewEntity> findByBookId(long aBookId);

}
