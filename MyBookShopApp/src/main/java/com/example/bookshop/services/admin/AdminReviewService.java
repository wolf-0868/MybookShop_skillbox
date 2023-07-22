package com.example.bookshop.services.admin;

import com.example.bookshop.data.dto.ReviewDTO;
import com.example.bookshop.exceptions.BookshopException;
import com.example.bookshop.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminReviewService {

    private final ReviewRepository reviewRepository;

    public Page<ReviewDTO> getPageReviews(Pageable aPageable) {
        return reviewRepository.findAll(aPageable)
                .map(ReviewDTO::of);
    }

    public Page<ReviewDTO> getPageOfSearchResultReviews(String aSearchWord, Pageable aPageable) {
        return reviewRepository.findByTextContainingIgnoreCase(aSearchWord, aPageable)
                .map(ReviewDTO::of);
    }

    public void deleteReviewById(Long aId) throws BookshopException {
        reviewRepository.delete(reviewRepository.findById(aId)
                .orElseThrow(() -> new BookshopException("Review by id='%d' not found", aId)));
    }

}
