package com.example.bookshop.data.dto;


import com.example.bookshop.data.entities.payments.TransactionEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
@ToString
@JsonPropertyOrder(value = {"time", "value", "description"})
public class TransactionDTO {

    @JsonIgnore
    private static final String DATE_TIME_FORMAT = "d MMMM yyyy HH:mm";

    @JsonIgnore
    private long id;

    @JsonIgnore
    private LocalDateTime dateTime;

    @JsonIgnore
    private int value;

    @JsonProperty(value = "description")
    private String description;

    public static TransactionDTO of(TransactionEntity aEntity) {
        return TransactionDTO.builder()
                .id(aEntity.getId())
                .dateTime(aEntity.getDatetime())
                .value(aEntity.getValue())
                .description(aEntity.getDescription())
                .build();
    }

    @JsonIgnore
    public String getDateTimeText() {
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    @JsonProperty(value = "time")
    public long getTime() {
        return ZonedDateTime.of(dateTime, ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    @JsonProperty(value = "value")
    public String getValueText() {
        String prefix = "";
        if (value > 0) {
            prefix = "+";
        } else if (value < 0) {
            prefix = "-";
        }
        return prefix + value;
    }

}
