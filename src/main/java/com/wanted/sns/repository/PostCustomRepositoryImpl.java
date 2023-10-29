package com.wanted.sns.repository;

import com.wanted.sns.domain.HashtagMapping;
import com.wanted.sns.domain.Order;
import com.wanted.sns.domain.Post;
import com.wanted.sns.domain.SnsType;
import com.wanted.sns.dto.PostRequest;
import com.wanted.sns.dto.PostResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {

  private final EntityManager entityManager;

  @Override
  public List<PostResponse> findAllPostBy(PostRequest postRequest) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<PostResponse> query = builder.createQuery(PostResponse.class);
    Root<Post> post = query.from(Post.class);

    int pageOffset = postRequest.page() * postRequest.pageCount();
    List<Predicate> searchCriteria = new ArrayList<>();

    query.select(builder.construct(PostResponse.class, post));

    if (postRequest.hashtag() != null) {
      searchCriteria.add(post.get("seq").in(findAllPostByHashtag(postRequest)));
    }

    return entityManager
        .createQuery(buildCommonQuery(postRequest, searchCriteria, builder, post, query))
        .setFirstResult(pageOffset)
        .setMaxResults(postRequest.pageCount())
        .getResultList();
  }

  public List<PostResponse> findAllPostByHashtag(PostRequest postRequest) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> query = builder.createQuery(Long.class);
    Root<Post> post = query.from(Post.class);
    List<Predicate> searchCriteria = new ArrayList<>();
    Join<Post, HashtagMapping> hashtagMapping = post.join("hashtagMappingList", JoinType.RIGHT);

    query.select(post.get("seq"));

    if (postRequest.hashtag() != null) {
      searchCriteria.add(
          builder.equal(hashtagMapping.get("hashtag").get("name"), postRequest.hashtag()));
    }

    return entityManager.createQuery(
        buildCommonQuery(postRequest, searchCriteria, builder, post, query)).getResultList();
  }


  public CriteriaQuery buildCommonQuery(PostRequest postRequest, List<Predicate> searchCriteria,
      CriteriaBuilder builder, Root<Post> post, CriteriaQuery query) {

    if (postRequest.type() != null) {
      searchCriteria.add(builder.equal(post.get("type"), SnsType.find(postRequest.type())));
    }

    if (postRequest.searchBy() != null && postRequest.search() != null) {
      final String parameter = '%' + postRequest.search() + '%';

      if(!postRequest.searchBy().contains(",")){
        searchCriteria.add(builder.like(post.get(postRequest.searchBy()), parameter));
      }

      if(postRequest.searchBy().contains(",")){
        String[] searchArray = postRequest.searchBy().split(",");

        searchCriteria.add(builder.or(
            builder.like(post.get(searchArray[0]),parameter),
            builder.like(post.get(searchArray[1]), parameter)
        ));
      }
    }

    query.where(builder.and(searchCriteria.toArray(new Predicate[0])));

    if (postRequest.order().equals(Order.ASC.name())) {
      query.orderBy(builder.asc(post.get(postRequest.orderBy())));
    }

    if (postRequest.order().equals(Order.DESC.name())) {
      query.orderBy(builder.desc(post.get(postRequest.orderBy())));
    }

    return query;
  }
}
