package com.example.bookshop.services;

import com.example.bookshop.data.dto.BookReviewDTO;
import com.example.bookshop.data.dto.drafts.DraftBookReviewDTO;
import com.example.bookshop.data.entities.book.review.BookReviewEntity;
import com.example.bookshop.repositories.BookRepository;
import com.example.bookshop.repositories.BookReviewRepository;
import com.example.bookshop.repositories.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookReviewService(BookReviewRepository aBookReviewRepository, BookRepository aBookRepository, UserRepository aUserRepository) {
        bookReviewRepository = aBookReviewRepository;
        bookRepository = aBookRepository;
        userRepository = aUserRepository;
    }

    public List<BookReviewDTO> getReviewsByBookId(long aBookId) {
        return bookReviewRepository.findByBookId(aBookId)
                .stream()
                .map(BookReviewDTO::of)
                .collect(Collectors.toList());
    }

    public Long saveNewReview(@NonNull DraftBookReviewDTO aDraftReview) {
        return bookReviewRepository.save(createEntity(aDraftReview)).getId();
    }

    private BookReviewEntity createEntity(DraftBookReviewDTO aDraft) {
        BookReviewEntity entity = new BookReviewEntity();
        entity.setBook(bookRepository.findById(aDraft.getBookId()).orElseThrow());
        entity.setUser(userRepository.findById(aDraft.getUserId()).orElseThrow());
        entity.setText(aDraft.getText());
        return entity;
    }

    public List<BookReviewDTO> getAll() {
        return bookReviewRepository.findAll()
                .stream()
                .map(BookReviewDTO::of)
                .collect(Collectors.toList());
    }
}
