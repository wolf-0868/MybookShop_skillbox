package com.example.bookshop.services;

import com.example.bookshop.data.dto.BookDTO;
import com.example.bookshop.data.entities.book.links.Book2UserEntity;
import com.example.bookshop.data.entities.enums.BookBindingType;
import com.example.bookshop.exceptions.BookshopException;
import com.example.bookshop.repositories.Book2UserRepository;
import com.example.bookshop.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Book2UserService {

    private final BookRepository bookRepository;
    private final Book2UserRepository book2UserRepository;

    public Set<BookBindingType> findBindingTypesByBookForUser(long aBookId, long aUserId) {
        Set<BookBindingType> types = book2UserRepository.getAllBindingTypesByBook(aUserId, aBookId);
        return types.isEmpty() ? EnumSet.noneOf(BookBindingType.class) : EnumSet.copyOf(types);
    }

    public List<BookDTO> findBooksByBindingTypeForUser(long aUserId, BookBindingType aStatusType) {
        return book2UserRepository.findBooksByUSerIdAndType(aUserId, aStatusType)
                .stream()
                .map(BookDTO::of)
                .collect(Collectors.toList());
    }

    public List<BookDTO> getArchivedBooks(long aUserId) {
        return book2UserRepository.findBooksByUSerIdAndType(aUserId, BookBindingType.PAID)
                .stream()
                .filter(b -> b.getBindingUsers()
                        .stream()
                        .map(Book2UserEntity::getType)
                        .anyMatch(BookBindingType.ARCHIVED::equals))
                .map(BookDTO::of)
                .collect(Collectors.toList());
    }

    public List<BookDTO> getUnreadBooks(long aUserId) {
        return book2UserRepository.findBooksByUSerIdAndType(aUserId, BookBindingType.PAID)
                .stream()
                .filter(b -> b.getBindingUsers()
                        .stream()
                        .map(Book2UserEntity::getType)
                        .noneMatch(BookBindingType.ARCHIVED::equals))
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
        bookRepository.refreshPopularity(aBookId);
    }

    public void addToCart(Set<Long> aBookIds, long aUserId) {
        Set<Long> tempBooks = new HashSet<>();
        for (Long bookId : aBookIds) {
            if (!book2UserRepository.existsBookByBindingType(aUserId, bookId, BookBindingType.CART)) {
                tempBooks.add(bookId);
            }
        }
        for (Long bookId : tempBooks) {
            book2UserRepository.addBindingTypeForBook(aUserId, bookId, BookBindingType.CART);
            bookRepository.refreshPopularity(bookId);
        }
    }

    public void buyBooks(Set<Long> aBookIds, long aUserId) throws BookshopException {
        boolean noValid = false;
        List<Long> errorBooks = new ArrayList<>();
        for (Long bookId : aBookIds) {
            if (book2UserRepository.existsBookByBindingType(aUserId, bookId, BookBindingType.PAID)) {
                errorBooks.add(bookId);
                noValid = true;
            }
        }
        if (noValid) {
            throw new BookshopException("Ошибка при изменении статуса. Одна из книг уже является купленной. No validated bookIds=%s", errorBooks);
        }
        for (Long bookId : aBookIds) {
            book2UserRepository.deleteAllBindingTypesForBook(aUserId, bookId);
            book2UserRepository.addBindingTypeForBook(aUserId, bookId, BookBindingType.PAID);
            bookRepository.refreshPopularity(bookId);
        }
    }

}
