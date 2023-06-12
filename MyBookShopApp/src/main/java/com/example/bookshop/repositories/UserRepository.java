package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String aEmail);

    UserEntity findByPhone(String aPhone);

    boolean existsByEmail(String aEmail);

    boolean existsByPhone(String aPhone);

}
