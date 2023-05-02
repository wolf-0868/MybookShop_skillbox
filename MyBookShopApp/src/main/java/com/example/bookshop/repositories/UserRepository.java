package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.enums.BookBindingType;
import com.example.bookshop.data.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT case when count(b2u) > 0 then true else false end FROM Book2UserEntity b2u WHERE b2u.user.id = :user_id and b2u.book.id = :book_id and b2u.type = :type")
    boolean existsByBookStatus(
            @Param("user_id") long aUserId,
            @Param("book_id") long aBookId,
            @Param("type") BookBindingType aType);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO book2user (user_id, book_id, type) VALUES (:user_id, :book_id, :#{#type?.name()})")
    void addBookStatus(
            @Param("user_id") long aUserId,
            @Param("book_id") long aBookId,
            @Param("type") BookBindingType aType);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Book2UserEntity b2u WHERE b2u.user.id = :user_id and b2u.book.id = :book_id and b2u.type = :type")
    void deleteBookStatus(
            @Param("user_id") long aUserId,
            @Param("book_id") long aBookId,
            @Param("type") BookBindingType aType);

}
