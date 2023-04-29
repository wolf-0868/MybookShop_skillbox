package com.example.bookshop.repositories;

import com.example.bookshop.controllers.data.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findBySlug(String aSlug);

    Page<BookEntity> findByPubDateAfter(LocalDate aDate, Pageable aPageable);

    Page<BookEntity> findByPubDateAfterAndPubDateBefore(LocalDate aStartDate, LocalDate aEndDate, Pageable aPageable);

    Page<BookEntity> findByTitleContainingIgnoreCase(String aBookTitle, Pageable aPageable);

    @Query(value = "SELECT b2a.book FROM Book2GenreEntity b2a WHERE b2a.genre.id = ?1")
    Page<BookEntity> findByGenreId(long aGenreId, Pageable aPageable);

    @Query(value = "SELECT b2a.book FROM Book2AuthorEntity b2a WHERE b2a.author.id = ?1")
    Page<BookEntity> findByAuthorId(long aAuthorId, Pageable aPageable);

    @Procedure(procedureName = "CALCULATE_BOOK_RATING_PROCEDURE")
    double calculateRatingBook(@Param("book_id_in") long aBookId);

}
