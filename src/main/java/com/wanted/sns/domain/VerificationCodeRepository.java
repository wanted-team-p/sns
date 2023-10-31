package com.wanted.sns.domain;

import java.util.*;
import org.springframework.data.jpa.repository.*;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findByMemberId(final Long memberId);

    boolean existsByMemberId(final Long memberId);

    void deleteByMemberId(final Long memberId);
}
