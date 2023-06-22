package com.example.bookshop.services;

import com.example.bookshop.data.entities.RatingBookEntity;
import com.example.bookshop.data.entities.book.review.BookReviewLikeEntity;
import com.example.bookshop.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingBookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookReviewRepository bookReviewRepository;
    private final RatingBookRepository ratingBookRepository;
    private final RatingBookReviewRepository ratingBookReviewRepository;

    public void updateRatingBook(long aBookId, long aUserId, short aValue) {
        RatingBookEntity rateBook = ratingBookRepository.findByUserIdAndBookId(aUserId, aBookId).orElse(null);
        if (rateBook == null) {
            rateBook = new RatingBookEntity();
            rateBook.setBook(bookRepository.findById(aBookId).orElseThrow());
            rateBook.setUser(userRepository.findById(aUserId).orElseThrow());
        }
        rateBook.setValue(aValue);
        ratingBookRepository.save(rateBook);
        bookRepository.refreshRating(aBookId);
    }

    public Map<Short, Long> getGroupingRating(long aBookId) {
        return ratingBookRepository.getByBookId(aBookId)
                .stream()
                .collect(Collectors.groupingBy(RatingBookEntity::getValue, Collectors.counting()));
    }

    public short getRatingBookForUser(long aUserId, long aBookId) {
        return ratingBookRepository.findByUserIdAndBookId(aUserId, aBookId)
                .map(RatingBookEntity::getValue)
                .orElse((short) 0);
    }

    public void updateRatingBookReview(long aReviewId, long aUserId, short aValue) {
        BookReviewLikeEntity rating = ratingBookReviewRepository.findByUserIdAndReviewId(aUserId, aReviewId)
                .orElse(null);
        if (rating == null) {
            rating = new BookReviewLikeEntity();
            rating.setReview(bookReviewRepository.findById(aReviewId).orElseThrow());
            rating.setUser(userRepository.findById(aUserId).orElseThrow());
        } else if ((rating.getValue() == aValue) || (aValue == 0)) {
            ratingBookReviewRepository.delete(rating);
            return;
        }
        rating.setValue(aValue);
        rating.setDatetime(LocalDateTime.now());
        ratingBookReviewRepository.save(rating);
    }

}
