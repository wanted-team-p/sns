package com.wanted.sns.service;

import com.wanted.sns.dto.StatisticResponse;
import com.wanted.sns.dto.StatisticRequest;
import com.wanted.sns.dto.ValidatedStatisticRequest;
import com.wanted.sns.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StatisticService {

    private final PostRepository postRepository;

    public List<StatisticResponse> getStatistics(StatisticRequest statisticRequest) {
        ValidatedStatisticRequest validatedStatisticRequest = new ValidatedStatisticRequest(statisticRequest);
        return postRepository.aggregateCount(validatedStatisticRequest);
    }

}

