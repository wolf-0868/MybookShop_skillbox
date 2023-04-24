package com.example.bookshop.services;

import com.example.bookshop.data.dto.BookDTO;
import com.example.bookshop.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository aBookRepository) {
        bookRepository = aBookRepository;
    }

    public List<BookDTO> getPageOfBooks(int aOffset, int aLimit) {
        return bookRepository.findAll(PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

    public List<BookDTO> getPageOfBooks(LocalDate aStartDate, LocalDate aEndDate, int aOffset, int aLimit) {
        return bookRepository.findBookEntitiesByPubDateAfterAndPubDateBefore(aStartDate, aEndDate, PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

    public List<BookDTO> getPageOfRecentBooks(int aOffset, int aLimit) {
        return bookRepository.findBookEntitiesByPubDateAfter(LocalDate.now()
                        .minusYears(3), PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

    public List<BookDTO> getPageOfPopularBooks(int aOffset, int aLimit) {
        return bookRepository.findBookEntitiesByBestsellerIsTrue(PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

    public List<BookDTO> getPageOfSearchResultBooks(String aSearchWord, Integer aOffset, Integer aLimit) {
        return bookRepository.findBookEntitiesByTitleContainingIgnoreCase(aSearchWord, PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

    public List<BookDTO> getBooksByGenreId(long aGenreId, Integer aOffset, Integer aLimit) {
        return bookRepository.findBookEntitiesByGenreId(aGenreId, PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

    public List<BookDTO> getBooksByAuthorId(long aAuthorId, Integer aOffset, Integer aLimit) {
        return bookRepository.findBookEntitiesByAuthorId(aAuthorId, PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

}
