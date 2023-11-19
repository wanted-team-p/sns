package com.wanted.sns.controller;


import com.wanted.sns.dto.PostResponse;
import com.wanted.sns.dto.PostRequest;
import com.wanted.sns.dto.PostResponse;
import com.wanted.sns.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable("id") long seq) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getPost(seq));

    @GetMapping
    public ResponseEntity<List<PostResponse>> getPostList(PostRequest postRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getPostList(postRequest));
    }

}
