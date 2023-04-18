package com.example.bookshop.services;

import com.example.bookshop.model.Book;
import com.example.bookshop.repositories.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private IBookRepository repository;

    @Autowired
    public BookService(IBookRepository aRepository) {
        repository = aRepository;
    }

    public List<Book> getAllBooks() {
        return repository.findAll().stream()
                .map(Book::of)
                .collect(Collectors.toList());
    }

    public List<Book> getPupularBooks() {
        return repository.findBooksByBestsellerIsTrue().stream()
                .map(Book::of)
                .collect(Collectors.toList());
    }

    public List<Book> getRecentBooks() {
        return repository.findBooksByPubDateIsAfter(LocalDate.now()
                .minusYears(3)).stream()
                .map(Book::of)
                .collect(Collectors.toList());
    }

    public Page<Book> getPageOfSearchResultBooks(String aSearchWord, Integer aOffset, Integer aLimit) {
        return repository.findBookByTitleContaining(aSearchWord, PageRequest.of(aOffset, aLimit))
                .map(Book::of);
    }

}
