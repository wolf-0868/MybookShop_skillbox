package com.example.bookshop.data.payloads;

import com.example.bookshop.data.entities.enums.BookBindingType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ChangeBookStatusPayload {

    private Set<Long> booksIds = new HashSet<>();
    private BookBindingType status;

}
