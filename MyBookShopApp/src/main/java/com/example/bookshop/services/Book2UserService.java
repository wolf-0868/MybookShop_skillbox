package com.example.bookshop.services;

import com.example.bookshop.data.dto.BookDTO;
import com.example.bookshop.data.entities.enums.BookBindingType;
import com.example.bookshop.repositories.Book2UserRepository;
import com.example.bookshop.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Book2UserService {

    private final BookRepository bookRepository;
    private final Book2UserRepository book2UserRepository;

    @Autowired
    public Book2UserService(BookRepository aBookRepository, Book2UserRepository aBook2UserRepository) {
        bookRepository = aBookRepository;
        book2UserRepository = aBook2UserRepository;
    }

    public Set<String> getAllBindingTypesByBook(long aBookId, long aUserId) {
        return book2UserRepository.getAllBindingTypesByBook(aUserId, aBookId)
                .stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    public List<BookDTO> getBooksByBindingTypeForUser(long aUserId, BookBindingType aStatusType) {
        return book2UserRepository.findBooksByUSerIdAndType(aUserId, aStatusType)
                .stream()
                .map(BookDTO::of)
                .collect(Collectors.toList());
    }

    public void changeBookindingType(long aBookId, long aUserId, BookBindingType aStatus) {
        if (BookBindingType.UNLINK == aStatus) {
            book2UserRepository.deleteAllBindingTypesForBook(aUserId, aBookId);
        } else {
            if (!book2UserRepository.existsBookByBindingType(aUserId, aBookId, aStatus)) {
                book2UserRepository.addBindingTypeForBook(aUserId, aBookId, aStatus);
            } else {
                book2UserRepository.deleteBindingTypeForBook(aUserId, aBookId, aStatus);
            }
        }
        bookRepository.refreshRating(aBookId);
    }

}
