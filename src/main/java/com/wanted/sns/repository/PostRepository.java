package com.wanted.sns.repository;

import com.wanted.sns.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {
}
