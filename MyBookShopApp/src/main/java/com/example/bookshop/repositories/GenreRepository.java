package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

    Optional<GenreEntity> findBySlug(String aSlug);

    @Query(value = "SELECT b2g.genre FROM Book2GenreEntity b2g WHERE b2g.book.id = ?1")
    Set<GenreEntity> findByBookId(long aBookId);

}
