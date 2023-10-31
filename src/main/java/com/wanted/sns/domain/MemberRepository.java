package com.wanted.sns.domain;

import org.springframework.data.jpa.repository.*;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmailValue(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
