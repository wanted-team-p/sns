package com.wanted.sns.service;

import com.wanted.sns.domain.*;
import com.wanted.sns.dto.*;
import com.wanted.sns.exception.*;
import com.wanted.sns.support.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final Encryptor encryptor;

    @Transactional
    public void signUp(final SignUpRequest request) {
        checkDuplicateEmail(request.email());
        checkDuplicatePhoneNumber(request.phoneNumber());

        final String encryptedPassword = encryptor.encrypt(new PlainPassword(request.password()));
        memberRepository.save(request.toEntity(encryptedPassword));
    }

    private void checkDuplicateEmail(final String email) {
        if (memberRepository.existsByEmailValue(email)) {
            throw new BusinessException("이미 존재하는 이메일입니다.");
        }
    }

    private void checkDuplicatePhoneNumber(final String phoneNumber) {
        if (memberRepository.existsByPhoneNumber(phoneNumber)) {
            throw new BusinessException("이미 존재하는 전화번호입니다.");
        }
    }
}
