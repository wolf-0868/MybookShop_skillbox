package com.example.bookshop.data.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "sms_keys")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class SmsCodeEntity implements Serializable {

    private static final long serialVersionUID = 1640888459832781195L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "expire_time")
    private LocalDateTime expireTime;


    public SmsCodeEntity(String aCode, int aExpireIn) {
        code = aCode;
        expireTime = LocalDateTime.now()
                .plusSeconds(aExpireIn);
    }

    public boolean isExpired() {
        return expireTime.isBefore(LocalDateTime.now());
    }

}
