package com.example.football_service.domain.common.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class SportEventStatus {
    private String status;
    private String match_status;
    private String home_score;
    private String away_score;
    private List<PeriodScore> period_scores;
    private Clock clock;

}
