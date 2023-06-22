package com.example.bookshop.data.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RateBookPayload {

    private long bookId;
    private short value;

}
