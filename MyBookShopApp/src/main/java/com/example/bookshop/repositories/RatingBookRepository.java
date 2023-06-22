package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.RatingBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingBookRepository extends JpaRepository<RatingBookEntity, Long> {

    List<RatingBookEntity> getByBookId(long aBookId);

    Optional<RatingBookEntity> findByUserIdAndBookId(long aUserId, long aBookId);

}
