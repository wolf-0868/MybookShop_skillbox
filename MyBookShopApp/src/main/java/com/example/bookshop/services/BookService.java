package com.example.bookshop.services;

import com.example.bookshop.data.dto.BookDTO;
import com.example.bookshop.data.entities.BookEntity;
import com.example.bookshop.exceptions.DataNotFoundException;
import com.example.bookshop.repositories.Book2UserRepository;
import com.example.bookshop.repositories.BookRepository;
import com.example.bookshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final Book2UserRepository book2UserRepository;

    @Autowired
    public BookService(BookRepository aBookRepository, UserRepository aUserRepository, Book2UserRepository aBook2UserRepository) {
        bookRepository = aBookRepository;
        userRepository = aUserRepository;
        book2UserRepository = aBook2UserRepository;
    }

    public BookDTO FindById(long aBookId) throws DataNotFoundException {
        return bookRepository.findById(aBookId)
                .map(BookDTO::of)
                .orElseThrow(() -> new DataNotFoundException(Map.of("id", aBookId), BookEntity.class.getName()));
    }

    public BookDTO getBookBySlug(String aSlug) throws DataNotFoundException {
        return bookRepository.findBySlug(aSlug)
                .map(BookDTO::of)
                .orElseThrow(() -> new DataNotFoundException(Map.of("slug", aSlug), BookEntity.class.getName()));
    }

    public List<BookDTO> getPageOfBooks(LocalDate aStartDate, LocalDate aEndDate, int aOffset, int aLimit) {
        return bookRepository.findByPubDateAfterAndPubDateBefore(aStartDate, aEndDate, PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

    public List<BookDTO> getRecommendedBooks(int aOffset, int aLimit) {
        return bookRepository.findAllOrderByRating(PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

    public List<BookDTO> getPageOfRecentBooks(int aOffset, int aLimit) {
        return bookRepository.findByPubDateAfter(LocalDate.now()
                        .minusYears(3), PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

    public List<BookDTO> getPageOfPopularBooks(int aOffset, int aLimit) {
        return bookRepository.findAll(PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

    public List<BookDTO> getPageOfSearchResultBooks(String aSearchWord, int aOffset, int aLimit) {
        return bookRepository.findByTitleContainingIgnoreCase(aSearchWord, PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

    public List<BookDTO> getBooksByGenreId(long aGenreId, int aOffset, int aLimit) {
        return bookRepository.findByGenreId(aGenreId, PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

    public List<BookDTO> getBooksByAuthorId(long aAuthorId, int aOffset, int aLimit) {
        return bookRepository.findByAuthorId(aAuthorId, PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

}
