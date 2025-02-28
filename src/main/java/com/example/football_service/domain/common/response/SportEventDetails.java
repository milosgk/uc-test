package com.example.football_service.domain.common.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SportEventDetails {
    private String id;
    private List<Competitor> competitors;
    private String start_time;
    private SportEventContext sport_event_context;

}
