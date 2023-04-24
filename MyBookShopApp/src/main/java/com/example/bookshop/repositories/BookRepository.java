package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Page<BookEntity> findBookEntitiesByBestsellerIsTrue(Pageable aPageable);

    Page<BookEntity> findBookEntitiesByPubDateAfter(LocalDate aDate, Pageable aPageable);

    Page<BookEntity> findBookEntitiesByPubDateAfterAndPubDateBefore(LocalDate aStartDate, LocalDate aEndDate, Pageable aPageable);

    Page<BookEntity> findBookEntitiesByTitleContainingIgnoreCase(String aBookTitle, Pageable aPageable);

    @Query(value = "SELECT b.book FROM Book2GenreEntity b WHERE b.genre.id = ?1")
    Page<BookEntity> findBookEntitiesByGenreId(Long aGenreId, Pageable aPageable);

    @Query(value = "SELECT b.book FROM Book2AuthorEntity b WHERE b.author.id = ?1")
    Page<BookEntity> findBookEntitiesByAuthorId(Long aAuthorId, Pageable aPageable);

}
