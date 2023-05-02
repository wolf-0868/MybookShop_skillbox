package com.example.bookshop.services;

import com.example.bookshop.data.dto.BookDTO;
import com.example.bookshop.data.entities.enums.BookBindingType;
import com.example.bookshop.repositories.BookRepository;
import com.example.bookshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    @Autowired
    public BookService(BookRepository aBookRepository, UserRepository aUserRepository) {
        bookRepository = aBookRepository;
        userRepository = aUserRepository;
    }

    public BookDTO FindById(long aBookId) {
        return bookRepository.findById(aBookId)
                .map(BookDTO::of)
                .orElse(null);
    }

    public BookDTO getBookBySlug(String aSlug) {
        return bookRepository.findBySlug(aSlug)
                .map(BookDTO::of)
                .orElse(null);
    }

    public List<BookDTO> getPageOfBooks(int aOffset, int aLimit) {
        return bookRepository.findAll(PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

    public List<BookDTO> getPageOfBooks(LocalDate aStartDate, LocalDate aEndDate, int aOffset, int aLimit) {
        return bookRepository.findByPubDateAfterAndPubDateBefore(aStartDate, aEndDate, PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

    public List<BookDTO> getRecommendedBooks(int aOffset, int aLimit) {
        return bookRepository.findByRecommendedBooks(PageRequest.of(aOffset / aLimit, aLimit))
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

    public List<BookDTO> getPageOfSearchResultBooks(String aSearchWord, Integer aOffset, Integer aLimit) {
        return bookRepository.findByTitleContainingIgnoreCase(aSearchWord, PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

    public List<BookDTO> getBooksByGenreId(long aGenreId, Integer aOffset, Integer aLimit) {
        return bookRepository.findByGenreId(aGenreId, PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

    public List<BookDTO> getBooksByAuthorId(long aAuthorId, Integer aOffset, Integer aLimit) {
        return bookRepository.findByAuthorId(aAuthorId, PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

    public void changeBookStatus(long aBookId, long aUserId, BookBindingType aStatus) {
        if (userRepository.existsById(aUserId) && bookRepository.existsById(aBookId)) {
            if (!userRepository.existsByBookStatus(aUserId, aBookId, aStatus)) {
                userRepository.addBookStatus(aUserId, aBookId, aStatus);
            } else {
                userRepository.deleteBookStatus(aUserId, aBookId, aStatus);
            }
        }
    }

}
