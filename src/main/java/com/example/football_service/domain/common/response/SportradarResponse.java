package com.example.football_service.domain.common.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class SportradarResponse {
    private String generated_at;
    private List<SportEvent> schedules;

}
