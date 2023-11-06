package com.wanted.sns.repository;

import com.wanted.sns.domain.CountType;
import com.wanted.sns.domain.HashtagMapping;
import com.wanted.sns.domain.Order;
import com.wanted.sns.domain.Post;
import com.wanted.sns.domain.SnsType;
import com.wanted.sns.domain.SortType;
import com.wanted.sns.dto.PostRequest;
import com.wanted.sns.dto.PostResponse;
import com.wanted.sns.dto.StatisticResponse;
import com.wanted.sns.dto.ValidatedStatisticRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<PostResponse> findAllPostBy(PostRequest postRequest) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> query = builder.createQuery(Post.class);
        Root<Post> post = query.from(Post.class);
        Fetch<Post, HashtagMapping> hashtagMapping = post.fetch("hashtagMappingList");
        hashtagMapping.fetch("hashtag", JoinType.LEFT);

        int pageOffset = postRequest.page() * postRequest.pageCount();
        List<Predicate> searchCriteria = new ArrayList<>();

        query.select(post);

        if (postRequest.hashtag() != null) {
            searchCriteria.add(post.get("seq").in(findAllPostByHashtag(postRequest)));
        }

        List<Post> result = entityManager.createQuery(
                        buildCommonQuery(postRequest, searchCriteria, builder, post, query)
                ).setFirstResult(pageOffset)
                .setMaxResults(postRequest.pageCount())
                .getResultList();

        return result.stream().map(PostResponse::new).collect(Collectors.toList());
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
                buildCommonQuery(postRequest, searchCriteria, builder, post, query)
        ).getResultList();
    }


    public CriteriaQuery buildCommonQuery(PostRequest postRequest, List<Predicate> searchCriteria,
                                          CriteriaBuilder builder, Root<Post> post, CriteriaQuery query) {

        if (postRequest.type() != null) {
            searchCriteria.add(builder.equal(post.get("type"), SnsType.find(postRequest.type())));
        }

        if (postRequest.searchBy() != null && postRequest.search() != null) {
            final String parameter = '%' + postRequest.search() + '%';

            if (!postRequest.searchBy().contains(",")) {
                searchCriteria.add(builder.like(post.get(postRequest.searchBy()), parameter));
            }

            if (postRequest.searchBy().contains(",")) {
                String[] searchArray = postRequest.searchBy().split(",");

                searchCriteria.add(builder.or(
                        builder.like(post.get(searchArray[0]), parameter),
                        builder.like(post.get(searchArray[1]), parameter)
                ));
            }
        }

        query.where(builder.and(searchCriteria.toArray(new Predicate[0])));

        if (postRequest.order().equals(Order.ASC.getValue())) {
            query.orderBy(builder.asc(post.get(postRequest.orderBy())));
        }

        if (postRequest.order().equals(Order.DESC.getValue())) {
            query.orderBy(builder.desc(post.get(postRequest.orderBy())));
        }

        return query;
    }

    @Override
    public List<StatisticResponse> aggregateCount(ValidatedStatisticRequest request) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StatisticResponse> query = builder.createQuery(StatisticResponse.class);
        Root<Post> post = query.from(Post.class);

        Expression<String> createdAtExpression = getCreatedAtExpressionBySortType(builder, post, request.getSortType());
        Expression<Long> countExpression = getCountExpression(builder, post, request.getCountType());

        query.select(
                builder.construct(StatisticResponse.class, createdAtExpression, countExpression));

        LocalDateTime start = request.getStart();
        LocalDateTime end = request.getEnd()
                .plusDays(1L)
                .minusNanos(1L);
        Predicate createdAtBetweenStartAndEnd = builder.between(post.get("createdAt"), start, end);
        query.where(createdAtBetweenStartAndEnd);

        query.groupBy(createdAtExpression);
        query.orderBy(builder.asc(createdAtExpression));

        return entityManager
                .createQuery(query)
                .getResultList();
    }

    private Expression<String> getCreatedAtExpressionBySortType(CriteriaBuilder builder, Root<Post> post, SortType sortType) {
        String format = "YYYY-mm-dd";
        if (sortType.equals(SortType.HOUR)) {
            format = "YYYY-mm-dd HH";
        }
        return builder.function("to_char", String.class, post.get("createdAt"), builder.literal(format));
    }

    private Expression<Long> getCountExpression(CriteriaBuilder builder, Root<Post> post, CountType countType) {
        if (countType.equals(CountType.POST)) {
            return builder.count(post.get(CountType.POST.target));
        }
        return builder.sumAsLong(post.get(countType.target));
    }

}
