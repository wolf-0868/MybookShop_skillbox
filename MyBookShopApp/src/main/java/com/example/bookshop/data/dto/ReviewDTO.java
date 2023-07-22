package com.example.bookshop.data.dto;

import com.example.bookshop.data.entities.book.review.BookReviewEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewDTO {

    private Long id;
    private String text;
    private String book;
    private String user;

    public static ReviewDTO of(BookReviewEntity aEntity) {
        return ReviewDTO.builder()
                .id(aEntity.getId())
                .text(aEntity.getText())
                .book(aEntity.getBook().getTitle())
                .user(aEntity.getUser().getName())
                .build();
    }

}
