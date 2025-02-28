package com.example.football_service.domain.common.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TeamsResponse {
    private String generated_at;
    private List<TeamResponse> season_competitors;
}
