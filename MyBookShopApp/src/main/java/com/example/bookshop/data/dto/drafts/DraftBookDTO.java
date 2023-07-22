package com.example.bookshop.data.dto.drafts;

import com.example.bookshop.data.entities.BookEntity;
import com.example.bookshop.data.entities.book.links.Book2AuthorEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DraftBookDTO {

    private Long id;
    private String slug;
    private String title;
    private String image;
    private Long authorId;
    private String authorFullName;
    private boolean bestseller;
    private int price;
    private int discount;
    private double rating;
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pubDate;

    public static DraftBookDTO of(BookEntity aEntity) {
        DraftBookDTOBuilder builder = DraftBookDTO.builder()
                .id(aEntity.getId())
                .slug(aEntity.getSlug())
                .title(aEntity.getTitle())
                .image(aEntity.getImage())
                .pubDate(aEntity.getPubDate())
                .bestseller(aEntity.isBestseller())
                .price(aEntity.getPrice())
                .discount(aEntity.getDiscount())
                .rating(aEntity.getRating())
                .description(aEntity.getDescription());
        aEntity.getAuthors()
                .stream()
                .findFirst()
                .map(Book2AuthorEntity::getAuthor)
                .ifPresent(authorEntity -> builder.authorId(authorEntity.getId())
                        .authorFullName(authorEntity.getFirstname() + " " + authorEntity.getLastname()));
        return builder.build();
    }

}
