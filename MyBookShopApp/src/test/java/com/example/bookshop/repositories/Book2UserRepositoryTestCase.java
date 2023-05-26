package com.example.bookshop.repositories;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static com.example.bookshop.data.entities.enums.BookBindingType.UNLINK;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource("/application-test.properties")
class Book2UserRepositoryTestCase {

    private final Book2UserRepository book2UserRepository;

    @Autowired
    Book2UserRepositoryTestCase(Book2UserRepository aBook2UserRepository) {
        book2UserRepository = aBook2UserRepository;
    }

    @Order(1)
    @Test
    void testExistsBookByStatus() {
        assertFalse(book2UserRepository.existsBookByBindingType(1L, 1L, UNLINK));
    }

    @Order(2)
    @Test
    void testAddBookStatus() {
        book2UserRepository.addBindingTypeForBook(1L, 1L, UNLINK);
        assertTrue(book2UserRepository.existsBookByBindingType(1L, 1L, UNLINK));
    }

    @Order(3)
    @Test
    void testGetAllStatusesByBookTrue() {
        assertFalse(book2UserRepository.getAllBindingTypesByBook(1L, 1L).isEmpty());
    }

    @Order(4)
    @Test
    void deleteBookStatus() {
        book2UserRepository.deleteBindingTypeForBook(1L, 1L, UNLINK);
        assertFalse(book2UserRepository.existsBookByBindingType(1L, 1L, UNLINK));
    }

}
