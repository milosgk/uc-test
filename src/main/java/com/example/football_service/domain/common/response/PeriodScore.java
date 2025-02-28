package com.example.football_service.domain.common.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeriodScore {
    private int home_score;
    private int away_score;
    private String type;
    private int number;

}
