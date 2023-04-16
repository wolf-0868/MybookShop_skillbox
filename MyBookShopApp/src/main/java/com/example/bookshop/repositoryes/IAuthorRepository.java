package com.example.bookshop.repositoryes;

import com.example.bookshop.data.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorRepository extends JpaRepository<AuthorEntity, Long> {}
