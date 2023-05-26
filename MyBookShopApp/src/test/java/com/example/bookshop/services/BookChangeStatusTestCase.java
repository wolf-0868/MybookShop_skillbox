package com.example.bookshop.services;

import com.example.bookshop.repositories.Book2UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static com.example.bookshop.data.entities.enums.BookBindingType.ARCHIVED;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource("/application-test.properties")
class BookChangeStatusTestCase {

    private final Book2UserService book2UserService;
    private final Book2UserRepository book2UserRepository;

    private boolean isEditDB = false;

    @Autowired
    BookChangeStatusTestCase(Book2UserService aBook2UserService, Book2UserRepository aBook2UserRepository) {
        book2UserService = aBook2UserService;
        book2UserRepository = aBook2UserRepository;
    }

    @BeforeAll
    void setUp() {
        if (book2UserRepository.existsBookByBindingType(1L, 1L, ARCHIVED)) {
            isEditDB = true;
            book2UserRepository.deleteBindingTypeForBook(1L, 1L, ARCHIVED);
        }
    }

    @AfterAll
    void tearDown() {
        if (isEditDB) {
            book2UserRepository.addBindingTypeForBook(1L, 1L, ARCHIVED);
        }
    }

    @Test
    @Order(1)
    void testAddStatus() {
        book2UserService.changeBookindingType(1L, 1L, ARCHIVED);
        assertTrue(book2UserRepository.existsBookByBindingType(1L, 1L, ARCHIVED));
    }

    @Test
    @Order(2)
    void testDeleteStatus() {
        book2UserService.changeBookindingType(1L, 1L, ARCHIVED);
        assertFalse(book2UserRepository.existsBookByBindingType(1L, 1L, ARCHIVED));
    }

}
