package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.BookEntity;
import com.google.common.collect.Ordering;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookRepositoryTestCase {

    private final BookRepository bookRepository;

    @Autowired
    BookRepositoryTestCase(BookRepository aBookRepository) {
        bookRepository = aBookRepository;
    }

    @Test
    void testFindById() {
        assertNotNull(bookRepository.findById(1l)
                .orElse(null));
    }

    @Test
    void testFindByPubDateAfterAndPubDateBefore() {
        LocalDate start = LocalDate.parse("2020-01-01");
        LocalDate end = LocalDate.parse("2023-01-01");
        assertTrue(bookRepository.findByPubDateAfterAndPubDateBefore(start, end, PageRequest.ofSize(100))
                .stream()
                .map(BookEntity::getPubDate)
                .anyMatch(d -> d.isAfter(start) && d.isBefore(end)));
    }

    @Test
    void testFindByTitleContainingIgnoreCase() {
        assertTrue(bookRepository.findByTitleContainingIgnoreCase("Body", PageRequest.ofSize(100))
                .stream()
                .map(BookEntity::getTitle)
                .anyMatch(t -> t.contains("Body")));
    }

    @Test
    void testFindAllOrderByRating() {
        List<BookEntity> books = bookRepository.findAllOrderByRating(PageRequest.ofSize(100)).getContent();
        assertTrue(Ordering.from(Comparator.comparingDouble(BookEntity::getRating).reversed()).isOrdered(books));
    }

    @Test
    void testCalculateRatingForBook() {
        assertNotNull(bookRepository.calculateRatingForBook(1L));
    }

}
