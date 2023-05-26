package com.example.bookshop.data.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactConfirmationPayload {

    private String contact;
    private String code;

}
