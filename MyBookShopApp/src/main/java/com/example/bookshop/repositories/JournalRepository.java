package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.JournalEntity;
import com.example.bookshop.data.entities.enums.JournalEntryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface JournalRepository extends JpaRepository<JournalEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT DISTINCT ON (journal.book_id) * FROM journal WHERE user_id = :user_id AND type = :#{#type?.ordinal()} AND datetime > :start_date AND datetime <= :end_date",
            countQuery = "SELECT DISTINCT ON (journal.book_id) count(*) FROM journal WHERE user_id = :user_id AND type = :#{#type?.ordinal()} AND datetime > :start_date AND datetime <= :end_date")
    Page<JournalEntity> getJournalEntries(
            @Param("start_date") LocalDateTime aStartDate,
            @Param("end_date") LocalDateTime aEndDate,
            @Param("user_id") long aUserId,
            @Param("type") JournalEntryType aType,
            Pageable aPageable);

}
