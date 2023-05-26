package com.example.bookshop.data.dto.drafts;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DraftBookReviewDTO {

    private Long bookId;
    private Long userId;
    private String text;

}
