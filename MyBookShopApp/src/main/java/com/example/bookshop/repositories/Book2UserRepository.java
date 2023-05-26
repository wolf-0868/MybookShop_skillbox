package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.BookEntity;
import com.example.bookshop.data.entities.book.links.Book2UserEntity;
import com.example.bookshop.data.entities.enums.BookBindingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface Book2UserRepository extends JpaRepository<Book2UserEntity, Long> {

    @Query(value = "SELECT b2u.book FROM Book2UserEntity b2u WHERE b2u.user.id = :user_id AND b2u.type = :type")
    List<BookEntity> findBooksByUSerIdAndType(
            @Param("user_id") long aUserId,
            @Param("type") BookBindingType aType);

    @Query(value = "SELECT case when count(b2u) > 0 then true else false end FROM Book2UserEntity b2u WHERE b2u.user.id = :user_id and b2u.book.id = :book_id and b2u.type = :type")
    boolean existsBookByBindingType(
            @Param("user_id") long aUserId,
            @Param("book_id") long aBookId,
            @Param("type") BookBindingType aType);


    @Query(value = "SELECT b2u.type FROM Book2UserEntity b2u WHERE b2u.user.id = :user_id and b2u.book.id = :book_id")
    Set<BookBindingType> getAllBindingTypesByBook(
            @Param("user_id") long aUserId,
            @Param("book_id") long aBookId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO book2user (user_id, book_id, type) VALUES (:user_id, :book_id, :#{#type?.name()})")
    void addBindingTypeForBook(
            @Param("user_id") long aUserId,
            @Param("book_id") long aBookId,
            @Param("type") BookBindingType aType);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Book2UserEntity b2u WHERE b2u.user.id = :user_id and b2u.book.id = :book_id and b2u.type = :type")
    void deleteBindingTypeForBook(
            @Param("user_id") long aUserId,
            @Param("book_id") long aBookId,
            @Param("type") BookBindingType aType);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Book2UserEntity b2u WHERE b2u.user.id = :user_id and b2u.book.id = :book_id")
    void deleteAllBindingTypesForBook(
            @Param("user_id") long aUserId,
            @Param("book_id") long aBookId);

}
