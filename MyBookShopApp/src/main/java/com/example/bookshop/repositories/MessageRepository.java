package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.book.review.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {}
