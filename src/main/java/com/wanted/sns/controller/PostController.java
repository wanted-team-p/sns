package com.wanted.sns.controller;

import com.wanted.sns.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/post")
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/like/{id}")
    public ResponseEntity<Void> likePost(@PathVariable("id") long seq) {
        postService.callLikeApi(seq);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/share/{id}")
    public ResponseEntity<Void> sharePost(@PathVariable("id") long seq) {
        postService.callShareApi(seq);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

}
