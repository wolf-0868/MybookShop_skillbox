package com.example.bookshop.data.payloads;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MessagePayload {

    private String name;
    private String mail;
    private String topic;
    private String message;
    private String sendMessage;

}
