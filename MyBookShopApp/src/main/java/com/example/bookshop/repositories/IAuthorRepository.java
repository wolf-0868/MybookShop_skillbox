package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorRepository extends JpaRepository<AuthorEntity, Long> {}
