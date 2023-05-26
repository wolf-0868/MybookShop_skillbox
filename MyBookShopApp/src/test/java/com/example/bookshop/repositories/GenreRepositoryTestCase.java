package com.example.bookshop.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class GenreRepositoryTestCase {

    private final GenreRepository genreRepository;

    @Autowired
    GenreRepositoryTestCase(GenreRepository aGenreRepository) {
        genreRepository = aGenreRepository;
    }

    @Test
    void testFindById() {
        assertNotNull(genreRepository.findById(1L).orElse(null));
    }

}
