package com.wanted.sns.domain;

import com.wanted.sns.exception.*;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import java.time.*;
import java.util.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
public class VerificationCode {

    private static final int CODE_LENGTH = 6;
    private static final int MIN_VERIFY_COUNT = 0;
    private static final int MAX_VERIFY_COUNT = 5;
    private static final long VALID_TIME_BOUND = 1L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private Long memberId;

    @CreatedDate
    private LocalDateTime createdAt;

    public VerificationCode(final String code, final Long memberId) {
        this(null, code, MIN_VERIFY_COUNT, memberId);
    }

    public VerificationCode(final Long id, final String code, final int count, final Long memberId) {
        validateCode(code);
        validateCount(count);
        this.id = id;
        this.code = code;
        this.count = count;
        this.memberId = memberId;
    }

    private void validateCode(final String code) {
        if (Objects.isNull(code)) {
            throw new BusinessException("인증코드는 null일 수 없습니다.");
        }

        if (code.length() != CODE_LENGTH) {
            throw new BusinessException(String.format("인증코드는 %d 자리여야 합니다.", CODE_LENGTH));
        }
    }

    private void validateCount(final int count) {
        if (count < MIN_VERIFY_COUNT) {
            throw new BusinessException("승인 요청 횟수는 음수가 될 수 없습니다.");
        }

        if (count > MAX_VERIFY_COUNT) {
            throw new BusinessException(String.format("승인 요청 횟수는 최대 %d회를 넘길 수 없습니다.", MAX_VERIFY_COUNT));
        }
    }

    public boolean isExpire(final LocalDateTime verificationTime) {
        return createdAt.plusHours(VALID_TIME_BOUND).isBefore(verificationTime);
    }

    public boolean isOverMaxCount() {
        return count >= MAX_VERIFY_COUNT;
    }

    public boolean isCorrect(final String code) {
        return this.code.equals(code);
    }

    public void increaseCount() {
        count++;
    }
}
