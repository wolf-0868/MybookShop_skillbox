package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.AuthorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    Optional<AuthorEntity> findBySlug(String aSlug);

    @Query(value = "SELECT b2a.author FROM Book2AuthorEntity b2a WHERE b2a.book.id = :book_id")
    Set<AuthorEntity> findByBookId(@Param("book_id") long aBookId);

    @Query(value = "SELECT a FROM AuthorEntity a WHERE a.firstname LIKE %:search_word% OR a.lastname LIKE %:search_word%")
    Page<AuthorEntity> findByFullName(@Param("search_word") String aSearchWord, Pageable aPageable);

}
