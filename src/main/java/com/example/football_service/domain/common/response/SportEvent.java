package com.example.football_service.domain.common.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SportEvent {
    private SportEventDetails sport_event;
    private SportEventStatus sport_event_status;

}
