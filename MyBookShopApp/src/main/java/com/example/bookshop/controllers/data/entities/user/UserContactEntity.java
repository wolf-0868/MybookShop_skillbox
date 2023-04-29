package com.example.bookshop.controllers.data.entities.user;


import com.example.bookshop.controllers.data.entities.enums.ContactType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "user_contact")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_contact_user_fk"), nullable = false)
    private UserEntity user;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private ContactType type;

    @Column(name = "approved", columnDefinition = "SMALLINT", nullable = false)
    private Short approved;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "code_trails")
    private Integer codeTrails;

    @Column(name = "code_datetime", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime codeDatetime;

    @Column(name = "contact", nullable = false)
    private String contact;

}
