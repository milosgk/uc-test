package com.example.football_service.domain.entities;

import com.example.football_service.domain.common.response.TeamResponse;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    String abbreviation;
    String competitorId;
    String name;
    String short_name;

    public Team(TeamResponse el) {
        this.abbreviation = el.getAbbreviation();
        this.competitorId = el.getId();
        this.name = el.getName();
        this.short_name = el.getShort_name();
    }

    public void updateFromResponse(TeamResponse el) {
        this.abbreviation = el.getAbbreviation();
        this.name = el.getName();
        this.short_name = el.getShort_name();
    }
}
