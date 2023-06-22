package com.example.bookshop.data.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RateBookReviewPayload {

    @JsonProperty(value = "reviewid")
    private long reviewId;

    @JsonProperty(value = "value")
    private short value;

}
