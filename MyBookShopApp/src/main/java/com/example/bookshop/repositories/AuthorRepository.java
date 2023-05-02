package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    Optional<AuthorEntity> findBySlug(String aSlug);

    @Query(value = "SELECT b2a.author FROM Book2AuthorEntity b2a WHERE b2a.book.id = ?1")
    Set<AuthorEntity> findByBookId(long aBookId);

}
