package com.example.bookshop.data.dto;

import com.example.bookshop.data.entities.BookEntity;
import com.example.bookshop.data.entities.book.review.BookReviewEntity;
import com.example.bookshop.data.entities.book.review.BookReviewLikeEntity;
import com.example.bookshop.data.entities.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Optional;

@Builder
@Getter
@ToString
@JsonPropertyOrder(value = {"id", "reviewer", "pubDate", "text", "likes", "dislikes", "rating"})
public class BookReviewDTO {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "reviewer")
    private String reviewer;

    @JsonProperty(value = "pubDate")
    private LocalDateTime pubDate = LocalDateTime.now();

    @JsonProperty(value = "text")
    private String text;

    @JsonProperty(value = "likes")
    private int countLikes;

    @JsonProperty(value = "dislikes")
    private int countDislikes;

    @JsonProperty(value = "rating")
    private int rating;

    @JsonIgnore
    private Long bookId;

    @JsonIgnore
    private Long userId;

    public static BookReviewDTO of(BookReviewEntity aEntity) {
        int likes = 0;
        int dislikes = 0;
        for (BookReviewLikeEntity like : aEntity.getLikes()) {
            switch (like.getValue()) {
                case 1:
                    likes++;
                    break;
                case -1:
                    dislikes++;
                    break;
                default:
                    break;
            }
        }
        BookReviewDTOBuilder bookReviewBuilder = BookReviewDTO.builder()
                .id(aEntity.getId())
                .pubDate(aEntity.getDatetime())
                .text(aEntity.getText())
                .countLikes(likes)
                .countDislikes(dislikes)
                .bookId(Optional.ofNullable(aEntity.getBook())
                        .map(BookEntity::getId)
                        .orElse(null))
                .rating(5);
        UserEntity user = aEntity.getUser();
        if (user != null) {
            bookReviewBuilder.userId(user.getId()).reviewer(user.getName());
        }
        return bookReviewBuilder.build();
    }

}
