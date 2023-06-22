package com.example.bookshop.services;

import com.example.bookshop.data.entities.book.review.MessageEntity;
import com.example.bookshop.data.payloads.MessagePayload;
import com.example.bookshop.repositories.MessageRepository;
import com.example.bookshop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public void saveNewMessage(MessagePayload aPayload, Long aUserId) {
        MessageEntity entity = new MessageEntity();
        entity.setDatetime(LocalDateTime.now());
        entity.setName(aPayload.getName());
        entity.setEmail(aPayload.getMail());
        entity.setSubject(aPayload.getTopic());
        entity.setText(aPayload.getMessage());
        if (aUserId != null) {
            entity.setUser(userRepository.findById(aUserId).orElseThrow());
        }
        messageRepository.save(entity);
    }

}
