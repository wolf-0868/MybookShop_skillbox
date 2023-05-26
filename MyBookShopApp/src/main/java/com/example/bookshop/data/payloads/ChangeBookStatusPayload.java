package com.example.bookshop.data.payloads;

import com.example.bookshop.data.entities.enums.BookBindingType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChangeBookStatusPayload {

    private List<Long> booksIds = new ArrayList<>();
    private BookBindingType status;

}
