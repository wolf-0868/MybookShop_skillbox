package com.example.bookshop.repositoryes;

import com.example.bookshop.data.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {

    List<Book> findBooksByBestsellerIsTrue();

    List<Book> findBooksByPubDateIsAfter(LocalDate aDate);

}
