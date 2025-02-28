package com.example.football_service.domain.common.response;


import com.example.football_service.domain.entities.Team;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamResponse {
    private String abbreviation;
    private String gender;
    private String id;
    private String name;
    private String virtual = "false";
    private String short_name;

    public TeamResponse() {
    }

    public TeamResponse(Team team) {
        this.abbreviation = team.getAbbreviation();
        this.id = team.getCompetitorId();
        this.name = team.getName();
        this.short_name = team.getShort_name();
        this.virtual = null;
        this.gender = null;
    }
}
