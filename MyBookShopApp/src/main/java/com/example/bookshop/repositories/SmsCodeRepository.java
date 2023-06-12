package com.example.bookshop.repositories;

import com.example.bookshop.data.entities.SmsCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsCodeRepository extends JpaRepository<SmsCodeEntity, Long> {

    public SmsCodeEntity findByCode(String aCode);

}
