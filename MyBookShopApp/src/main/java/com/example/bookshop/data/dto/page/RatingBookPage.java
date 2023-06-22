package com.example.bookshop.data.dto.page;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RatingBookPage {

    private final Map<Short, Long> ratingBookValues;

    public RatingBookPage(Map<Short, Long> aMap) {
        ratingBookValues = aMap;
    }

    public long getCountForRating(short aRate) {
        return ratingBookValues.getOrDefault(aRate, 0L);
    }

    public long getSumRating() {
        return ratingBookValues.values()
                .stream()
                .mapToLong(Long::longValue)
                .sum();
    }

}
