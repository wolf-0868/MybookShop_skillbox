package com.example.bookshop.services;

import com.example.bookshop.controllers.data.dto.BookReviewDTO;
import com.example.bookshop.repositories.BookReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;

    @Autowired
    public BookReviewService(BookReviewRepository aBookReviewRepository) {
        bookReviewRepository = aBookReviewRepository;
    }

    public List<BookReviewDTO> getReviersByBookId(long aBookId) {
        return bookReviewRepository.findByBookId(aBookId)
                .stream()
                .map(BookReviewDTO::of)
                .collect(Collectors.toList());
    }

}
