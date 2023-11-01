package com.wanted.sns.service;

import com.wanted.sns.domain.SnsType;
import com.wanted.sns.exception.ExternalApiException;
import com.wanted.sns.repository.PostRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;

    public void callLikeApi(long seq) {
        WebClient webClient = buildWebClient(getSnsType(seq));

        webClient.post()
                .uri(buildUri("/like/", String.valueOf(seq)))
                .retrieve()
                .onStatus(
                        HttpStatus.NOT_FOUND::equals,
                        response -> response.bodyToMono(String.class).map(ExternalApiException::new)
                )
                .bodyToMono(String.class)
                .block();

        postRepository.findPostBySeq(seq).increaseLikeCount();

    }

    public void callShareApi(long seq) {
        WebClient webClient = buildWebClient(getSnsType(seq));

        webClient.post()
                .uri(buildUri("/share/", String.valueOf(seq)))
                .retrieve()
                .onStatus(
                        HttpStatus.NOT_FOUND::equals,
                        response -> response.bodyToMono(String.class).map(ExternalApiException::new)
                )
                .bodyToMono(String.class)
                .block();

        postRepository.findPostBySeq(seq).increaseShareCount();
    }

    private SnsType getSnsType(long seq) {
        return postRepository.findById(seq)
                .orElseThrow(() -> new NoResultException("게시글을 찾을 수 없습니다."))
                .getType();
    }

    private String buildUri(String path, String pathVariable) {
        return UriComponentsBuilder.fromPath(path)
                .path(pathVariable)
                .build()
                .encode()
                .toUriString();
    }

    private WebClient buildWebClient(SnsType snsType) {
        return WebClient.builder()
                .baseUrl(snsType.getBaseurl())
                .build();
    }

}
