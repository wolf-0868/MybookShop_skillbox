package com.example.bookshop.data.dto.page;

import com.example.bookshop.data.entities.enums.BookBindingType;
import lombok.Getter;

import java.util.Set;

import static com.example.bookshop.data.entities.enums.BookBindingType.*;

@Getter
public class BookStatusesPageDTO {

    private final boolean isKept;
    private final boolean isCart;
    private final boolean isPaid;
    private final boolean isArchived;

    public BookStatusesPageDTO(Set<BookBindingType> aTypes) {
        isKept = aTypes.contains(KEPT);
        isCart = aTypes.contains(CART);
        isPaid = aTypes.contains(PAID);
        isArchived = aTypes.contains(ARCHIVED);
    }

}
