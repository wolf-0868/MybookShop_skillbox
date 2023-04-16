package com.example.bookshop.repositoryes;

import com.example.bookshop.data.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findBooksByBestsellerIsTrue();

    List<BookEntity> findBooksByPubDateIsAfter(LocalDate aDate);

}
