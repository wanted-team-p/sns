package com.wanted.sns.controller;

import com.wanted.sns.dto.StatisticResponse;
import com.wanted.sns.dto.StatisticRequest;
import com.wanted.sns.service.StatisticService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(path = "/statistic")
@RestController
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping(path = "/")
    private ResponseEntity<List<StatisticResponse>> statisticList(
            @Valid @ModelAttribute StatisticRequest statisticRequest
    ) {
        // TODO: JWT 토큰을 통해 인증, 유저명을 받음. 만약 hashtag가 null이면 유저명을 넣음

        System.out.println(statisticRequest.getStart());
        System.out.println(statisticRequest.getStart().getClass());
        System.out.println(statisticRequest.getEnd());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(statisticService.getStatistics(statisticRequest));
    }

}
