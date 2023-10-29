package com.wanted.sns.domain;

import jakarta.persistence.Id;
import jakarta.persistence.*;
import java.time.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Member {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String memberId;

    @Embedded
    private Email email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Member(final String memberId, final Email email, final String password, final LocalDate dateOfBirth,
                  final String name, final String phoneNumber,
                  final MemberRole role) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
