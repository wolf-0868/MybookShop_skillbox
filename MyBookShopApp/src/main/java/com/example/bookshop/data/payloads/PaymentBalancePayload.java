package com.example.bookshop.data.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentBalancePayload {

    private int sum;
    private long time;

}
