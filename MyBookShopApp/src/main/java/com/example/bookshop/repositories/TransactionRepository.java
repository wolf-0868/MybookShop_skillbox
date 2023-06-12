package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.payments.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @Query(value = "SELECT t FROM TransactionEntity t WHERE t.user.id = :user_id")
    Page<TransactionEntity> findByUserId(@Param("user_id") long aUserId, Pageable aPageable);

}
