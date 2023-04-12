package com.example.bookshop.services;

import com.example.bookshop.data.Book;
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

    public List<Book> getAllBooks() {
        return _repository.findAll();
    }

    public List<Book> getPupularBooks() {
        return _repository.findBooksByBestsellerIsTrue();
    }

    public List<Book> getRecentBooks() {
        return _repository.findBooksByPubDateIsAfter(LocalDate.now().minusYears(3));
    }

}
