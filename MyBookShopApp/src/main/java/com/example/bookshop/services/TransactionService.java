package com.example.bookshop.services;

import com.example.bookshop.data.dto.TransactionDTO;
import com.example.bookshop.data.entities.BookEntity;
import com.example.bookshop.data.entities.payments.TransactionEntity;
import com.example.bookshop.data.entities.user.UserEntity;
import com.example.bookshop.exceptions.TransactionException;
import com.example.bookshop.exceptions.UserNotFountException;
import com.example.bookshop.repositories.TransactionRepository;
import com.example.bookshop.repositories.UserRepository;
import com.example.bookshop.security.BookshopUserRegistrar;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final BookshopUserRegistrar userRegistrar;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public List<TransactionDTO> getPageTransactions(long aUserId, int aOffset, int aLimit) {
        return transactionRepository.findByUserId(aUserId, PageRequest.of(aOffset / aLimit, aLimit))
                .map(TransactionDTO::of)
                .getContent();
    }

    public void transactionBalance(long aUserId, int aSum) throws TransactionException, UserNotFountException {
        if (aSum <= 0) {
            throw new TransactionException("Пополняемая сумма не может быть меньше нуля, sum=%d", aSum);
        }
        UserEntity user = userRepository.findById(aUserId)
                .orElseThrow(() -> new TransactionException("Ошибка при получении пользователя"));
        user.setBalance(user.getBalance() + aSum);
        transactionRepository.save(createEntity(user, null, aSum));
        userRepository.save(user);
        userRegistrar.getCurrentUserEntity().setBalance(user.getBalance());
    }

    private TransactionEntity createEntity(@NonNull UserEntity aUser, BookEntity aBook, int aValue) {
        TransactionEntity entity = new TransactionEntity();
        entity.setDescription("Пополнение баланса");
        entity.setValue(aValue);
        entity.setBook(aBook);
        entity.setUser(aUser);
        entity.setDatetime(LocalDateTime.now());
        return entity;
    }

}
