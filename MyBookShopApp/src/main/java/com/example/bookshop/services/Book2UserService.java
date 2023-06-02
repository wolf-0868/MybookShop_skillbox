package com.example.bookshop.services;

import com.example.bookshop.data.dto.BookDTO;
import com.example.bookshop.data.entities.enums.BookBindingType;
import com.example.bookshop.repositories.Book2UserRepository;
import com.example.bookshop.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Book2UserService {

    private final BookRepository bookRepository;
    private final Book2UserRepository book2UserRepository;

    public Set<String> findBindingTypesByBookForUser(long aBookId, long aUserId) {
        return book2UserRepository.getAllBindingTypesByBook(aUserId, aBookId)
                .stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    public List<BookDTO> findBooksByBindingTypeForUser(long aUserId, BookBindingType aStatusType) {
        return book2UserRepository.findBooksByUSerIdAndType(aUserId, aStatusType)
                .stream()
                .map(BookDTO::of)
                .collect(Collectors.toList());
    }

    public void changeBookBindingType(long aBookId, long aUserId, BookBindingType aStatus) {
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
