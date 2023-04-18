package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findBooksByBestsellerIsTrue();

    List<BookEntity> findBooksByPubDateIsAfter(LocalDate aDate);

    Page<BookEntity> findBookByTitleContaining(String aBookTitle, Pageable aNextPage);

}
