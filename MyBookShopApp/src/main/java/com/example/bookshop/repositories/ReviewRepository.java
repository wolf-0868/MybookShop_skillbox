package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.book.review.BookReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<BookReviewEntity, Long> {

    Page<BookReviewEntity> findByTextContainingIgnoreCase(String aText, Pageable aPageable);

}
