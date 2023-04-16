package com.example.bookshop.services;

import com.example.bookshop.data.BookEntity;
import com.example.bookshop.repositoryes.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    private IBookRepository _repository;

    @Autowired
    public BookService(IBookRepository aRepository) {
        _repository = aRepository;
    }

    public List<BookEntity> getAllBooks() {
        return _repository.findAll();
    }

    public List<BookEntity> getPupularBooks() {
        return _repository.findBooksByBestsellerIsTrue();
    }

    public List<BookEntity> getRecentBooks() {
        return _repository.findBooksByPubDateIsAfter(LocalDate.now()
                .minusYears(3));
    }

}
