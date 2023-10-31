package com.wanted.sns.repository;

import com.wanted.sns.domain.HashtagMapping;
import com.wanted.sns.domain.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final EntityManager entityManager;

    @Override
    public Post findPostBySeq(long seq) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> query = builder.createQuery(Post.class);
        Root<Post> post = query.from(Post.class);

        Join<Post, HashtagMapping> hashtagMapping = (Join) post.fetch("hashtagMappingList");
        hashtagMapping.fetch("hashtag", JoinType.LEFT);

        query.select(post).where(builder.equal(post.get("seq"), seq));
        return entityManager.createQuery(query).getSingleResult();
    }

}
