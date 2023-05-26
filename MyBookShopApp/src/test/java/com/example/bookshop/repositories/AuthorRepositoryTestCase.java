package com.example.bookshop.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class AuthorRepositoryTestCase {

    private final AuthorRepository authorRepository;

    @Autowired
    AuthorRepositoryTestCase(AuthorRepository aAuthorRepository) {
        authorRepository = aAuthorRepository;
    }

    @Test
    void testFindById() {
        assertNotNull(authorRepository.findById(1l).orElse(null));
    }

}
