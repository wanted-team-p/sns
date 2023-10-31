package com.wanted.sns.controller;

import com.wanted.sns.dto.*;
import com.wanted.sns.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/verifications")
@RestController
public class VerificationController {

    private final VerificationService verificationService;

    @PostMapping
    public ResponseEntity<Void> create(final Long memberId) {
        verificationService.create(memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/check-code")
    public ResponseEntity<VerifyResponse> checkCode(final Long memberId, @RequestBody final VerifyRequest request) {
        final VerifyResponse response = verificationService.checkCode(memberId, request);
        return ResponseEntity.ok(response);
    }
}
