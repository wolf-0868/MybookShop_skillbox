package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

    Optional<GenreEntity> findGenreEntityBySlug(String aSlug);

}
