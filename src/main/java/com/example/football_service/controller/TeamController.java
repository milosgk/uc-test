package com.example.football_service.controller;

import com.example.football_service.domain.common.response.Match;
import com.example.football_service.domain.common.response.TeamResponse;
import com.example.football_service.domain.entities.UserFavoriteTeam;
import com.example.football_service.services.TeamService;
import com.example.football_service.util.Path;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(Path.API)
public class TeamController {

    private final TeamService teamService;

    @GetMapping(Path.SEASONS)
    public ResponseEntity<Set<TeamResponse>> getSeasonsByCompetition() throws JsonProcessingException {
        return ResponseEntity.ok( teamService.getSeasons());
    }

    @GetMapping(Path.TEAM + "/{teamName}")
    public ResponseEntity<List<TeamResponse>> getTeamsByName(@PathVariable(value = "teamName") String teamName) {
        return ResponseEntity.ok( teamService.getTeamsByName(teamName));
    }

    @PostMapping(Path.TEAM + "/{teamId}/{email}")
    public ResponseEntity<UserFavoriteTeam> addToFavorites(@PathVariable(value = "teamId") String teamId, @PathVariable(value = "email") String email) {
        return ResponseEntity.ok( teamService.addToFavorites(email, teamId));
    }

    @GetMapping(Path.TEAM)
    public ResponseEntity<List<Match>> getMatchesByDate( String date) {
        return ResponseEntity.ok( teamService.getMatchesForDate(LocalDate.parse(date)));
    }
}
