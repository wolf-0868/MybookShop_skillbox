package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.book.review.BookReviewLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingBookReviewRepository extends JpaRepository<BookReviewLikeEntity, Long> {

    Optional<BookReviewLikeEntity> findByUserIdAndReviewId(long aUserId, long aReviewId);

}
