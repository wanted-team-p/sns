package com.wanted.sns.service;

import com.wanted.sns.domain.*;
import com.wanted.sns.dto.*;
import com.wanted.sns.exception.*;
import com.wanted.sns.support.*;
import java.time.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VerificationService {

    private static final boolean CORRECT = true;
    private static final boolean INCORRECT = false;

    private final MemberRepository memberRepository;
    private final VerificationCodeRepository verificationCodeRepository;

    @Transactional
    public void create(final Long memberId) {
        final Member member = getMemberById(memberId);
        deleteVerificationCodeIfExists(member.getId());
        verificationCodeRepository.save(new VerificationCode(RandomNumberGenerator.generate(), member.getId()));
    }


    private void deleteVerificationCodeIfExists(final Long memberId) {
        final boolean existsVerificationCode = verificationCodeRepository.existsByMemberId(memberId);
        if (existsVerificationCode) {
            verificationCodeRepository.deleteByMemberId(memberId);
        }
    }

    @Transactional
    public VerifyResponse checkCode(final Long memberId, final VerifyRequest request) {
        final Member member = getMemberById(memberId);
        final VerificationCode verificationCode = getVerificationCodeByMemberId(memberId);

        validate(verificationCode);

        if (verificationCode.isCorrect(request.code())) {
            return completeVerification(member, verificationCode);
        }
        return failVerification(verificationCode);
    }

    private VerifyResponse completeVerification(final Member member, final VerificationCode verificationCode) {
        member.changeRoleToMember();
        verificationCodeRepository.delete(verificationCode);
        return new VerifyResponse(CORRECT);
    }

    private static VerifyResponse failVerification(final VerificationCode verificationCode) {
        verificationCode.increaseCount();
        return new VerifyResponse(INCORRECT);
    }

    private Member getMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(String.format("%d번 회원이 존재하지 않습니다.", memberId)));
    }

    private VerificationCode getVerificationCodeByMemberId(final Long memberId) {
        return verificationCodeRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException(String.format("%d번 회원의 인증코드가 존재하지 않습니다.", memberId)));
    }

    private static void validate(final VerificationCode verificationCode) {
        if (verificationCode.isExpire(LocalDateTime.now())) {
            throw new BusinessException("인증 가능 시간이 초과했습니다. 인증코드를 재발급해주세요.");
        }

        if (verificationCode.isOverMaxCount()) {
            throw new BusinessException("최대 인증 횟수를 초과했습니다. 인증코드를 재발급해주세요.");
        }
    }
}
