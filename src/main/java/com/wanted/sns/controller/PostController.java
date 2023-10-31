package com.wanted.sns.controller;

import com.wanted.sns.dto.PostResponse;
import com.wanted.sns.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/post")
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable("id") long seq) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getPost(seq));
    }

}
