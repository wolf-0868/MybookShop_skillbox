package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findBySlug(String aSlug);

    Page<BookEntity> findByPubDateAfter(LocalDate aDate, Pageable aPageable);

    Page<BookEntity> findByPubDateAfterAndPubDateBefore(LocalDate aStartDate, LocalDate aEndDate, Pageable aPageable);

    Page<BookEntity> findByTitleContainingIgnoreCase(String aBookTitle, Pageable aPageable);

    @Query(nativeQuery = true, value = "SELECT * FROM book b ORDER BY rating DESC ")
    Page<BookEntity> findAllOrderByRating(Pageable aPageable);

    @Query(value = "SELECT b2a.book FROM Book2GenreEntity b2a WHERE b2a.genre.id = :genre_id")
    Page<BookEntity> findByGenreId(
            @Param("genre_id") long aGenreId,
            Pageable aPageable);

    @Query(value = "SELECT b2a.book FROM Book2AuthorEntity b2a WHERE b2a.author.id = :author_id")
    Page<BookEntity> findByAuthorId(
            @Param("author_id") long aAuthorId,
            Pageable aPageable);

    @Query(nativeQuery = true, value = "SELECT calculate_book_rating_function(:book_id)")
    double calculateRatingForBook(@Param("book_id") long aBookId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE book SET rating = calculate_book_rating_function(:book_id) WHERE id = :book_id")
    void refreshRating(@Param("book_id") long aBookId);

}
