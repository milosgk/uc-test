package com.example.football_service.domain.common.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Match {
    private String id;
    private String competitionName;
    private String homeTeam;
    private String awayTeam;
    private String homeScore;
    private String awayScore;
    private String homeAbbreviation;
    private String awayAbbreviation;
    private String status;
    private String matchStatus;
    private String startTime;
    private List<PeriodScore> periodScores;
    private String clockPlayed;

    public Match(String homeTeam, String awayTeam, String homeAbbreviation, String awayAbbreviation, String homeScore, String awayScore,
                 String status, String matchStatus, String startTime, List<PeriodScore> periodScores, String competitionName, String clockPlayed) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeAbbreviation = homeAbbreviation;
        this.awayAbbreviation = awayAbbreviation;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.status = status;
        this.matchStatus = matchStatus;
        this.startTime = startTime;
        this.periodScores = periodScores;
        this.competitionName = competitionName;
        this.clockPlayed = clockPlayed;
    }

}
