package com.example.bookshop.services;

import com.example.bookshop.data.dto.BookDTO;
import com.example.bookshop.data.entities.BookEntity;
import com.example.bookshop.data.entities.JournalEntity;
import com.example.bookshop.data.entities.enums.JournalEntryType;
import com.example.bookshop.data.entities.user.UserEntity;
import com.example.bookshop.exceptions.DataNotFoundException;
import com.example.bookshop.repositories.BookRepository;
import com.example.bookshop.repositories.JournalRepository;
import com.example.bookshop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JournalService {

    private final JournalRepository journalRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public void logReviewBook(long aBookId, Long aUserId) throws DataNotFoundException {
        journalRepository.save(createEntity(
                userRepository.findById(aUserId)
                        .orElseThrow(() -> new DataNotFoundException(Map.of("id", aUserId),
                                UserEntity.class.getName())),
                bookRepository.findById(aBookId)
                        .orElseThrow(() -> new DataNotFoundException(Map.of("id", aBookId),
                                BookEntity.class.getName())),
                JournalEntryType.BOOK_REVIEW));
    }

    public List<BookDTO> getPageOfRecentlyBooks(long aUserId, int aOffset, int aLimit) {
        return journalRepository.getJournalEntries(LocalDateTime.now().minusWeeks(2),
                        LocalDateTime.now(), aUserId, JournalEntryType.BOOK_REVIEW,
                        PageRequest.of(aOffset / aLimit, aLimit))
                .map(JournalEntity::getBook)
                .map(BookDTO::of)
                .getContent();
    }

    private JournalEntity createEntity(UserEntity aUser, BookEntity aBook, JournalEntryType aType) {
        JournalEntity entity = new JournalEntity();
        entity.setUser(aUser);
        entity.setBook(aBook);
        entity.setType(aType);
        return entity;
    }
}
