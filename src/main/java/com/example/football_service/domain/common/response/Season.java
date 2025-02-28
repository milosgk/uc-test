package com.example.football_service.domain.common.response;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Season {
    private String competition_id;
    private boolean disabled;
    private String end_date;
    private String id;
    private String name;
    private String start_date;
    private String year;

}

