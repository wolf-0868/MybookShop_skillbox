package com.example.bookshop.services;

import com.example.bookshop.data.dto.BookDTO;
import com.example.bookshop.data.entities.BookEntity;
import com.example.bookshop.exceptions.DataNotFoundException;
import com.example.bookshop.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public BookDTO findById(long aBookId) throws DataNotFoundException {
        return bookRepository.findById(aBookId)
                .map(BookDTO::of)
                .orElseThrow(() -> new DataNotFoundException(Map.of("id", aBookId), BookEntity.class.getName()));
    }

    public BookDTO findBookBySlug(String aSlug) throws DataNotFoundException {
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

    public List<BookDTO> getPageByGenreId(long aGenreId, int aOffset, int aLimit) {
        return bookRepository.findByGenreId(aGenreId, PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

    public List<BookDTO> getPageByAuthorId(long aAuthorId, int aOffset, int aLimit) {
        return bookRepository.findByAuthorId(aAuthorId, PageRequest.of(aOffset / aLimit, aLimit))
                .map(BookDTO::of)
                .getContent();
    }

}
