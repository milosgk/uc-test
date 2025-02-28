package com.example.football_service.services;

import com.example.football_service.domain.common.response.*;
import com.example.football_service.domain.entities.AppUser;
import com.example.football_service.domain.entities.Team;
import com.example.football_service.domain.entities.UserFavoriteTeam;
import com.example.football_service.repository.TeamRepository;
import com.example.football_service.repository.UserFavoriteTeamRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;


@Service
public class TeamService {
    private final RestTemplate restTemplate;
    private final TeamRepository teamRepository;
    private final UserService userService;
    private final UserFavoriteTeamRepository userFavoriteTeamRepository;
    private static final String API_KEY = "VnPZy0UMsI9Z8p5wEGmEvacbGfhO1tVm1kTiXLd0";
    private static final List<String> COMPETITIONS = List.of(
            "sr:competition:17",
            "sr:competition:8",
            "sr:competition:7",
            "sr:competition:35"
    );

    public TeamService(RestTemplate restTemplate, TeamRepository teamRepository, UserService userService, UserFavoriteTeamRepository userFavoriteTeamRepository) {
        this.restTemplate = restTemplate;
        this.teamRepository = teamRepository;
        this.userService = userService;
        this.userFavoriteTeamRepository = userFavoriteTeamRepository;
    }

    public Set<TeamResponse> getSeasons() {
        Set<TeamResponse> teamSet = new HashSet<>();
        List<String> seasonIds = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        COMPETITIONS.parallelStream().forEach(competition -> {
            String seasonsUrl = String.format(
                    "https://api.sportradar.com/soccer-extended/production/v4/en/competitions/%s/seasons.json?api_key=%s", competition, API_KEY
            );

            try {
                ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                        seasonsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {
                        }
                );

                String jsonResponse = objectMapper.writeValueAsString(response.getBody());
                SeasonsResponse res = mapSeasonsJsonToObject(jsonResponse);

                res.getSeasons().stream()
                        .filter(el -> LocalDate.parse(el.getEnd_date()).isAfter(LocalDate.now()) &&
                                LocalDate.parse(el.getStart_date()).isBefore(LocalDate.now()))
                        .findFirst()
                        .ifPresent(el -> seasonIds.add(el.getId()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        seasonIds.parallelStream().forEach(seasonId -> {
            String teamsUrl = String.format(
                    "https://api.sportradar.com/soccer-extended/production/v4/en/seasons/%s/competitors.json?api_key=%s", seasonId, API_KEY
            );

            try {
                ResponseEntity<Map<String, Object>> teamsResponse = restTemplate.exchange(
                        teamsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {
                        }
                );

                String json = objectMapper.writeValueAsString(teamsResponse.getBody());
                TeamsResponse teamsRes = mapTeamsJsonToObject(json);

                teamsRes.getSeason_competitors().stream()
                        .filter(team -> !team.getVirtual().equals("true"))
                        .forEach(teamSet::add);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        teamSet.forEach(this::saveOrUpdateTeam);

        return teamSet;
    }

    public List<Match> getMatchesForDate(LocalDate date){
        List<Match> allMatches = new ArrayList<>();
        int offset = 0;
        boolean hasMoreMatches = true;

        while(hasMoreMatches) {
            String url = String.format("https://api.sportradar.com/soccer-extended/production/v4/en/schedules/%s/schedules.json?offset=%d&api_key=%s", date.toString(), offset, API_KEY);
            SportradarResponse response = restTemplate.getForObject(url, SportradarResponse.class);
            if (response != null && response.getSchedules() != null) {
                List<Match> matches = processMatches(response.getSchedules());
                allMatches.addAll(matches);
                hasMoreMatches = response.getSchedules().size() == 200;
                offset += 200;
            } else {
                hasMoreMatches = false;
            }
        }
        return allMatches;
    }

    private List<Match> processMatches(List<SportEvent> sportEvents) {
        List<Match> matches = new ArrayList<>();

        for (SportEvent sportEvent : sportEvents) {
            SportEventDetails details = sportEvent.getSport_event();
            SportEventStatus status = sportEvent.getSport_event_status();

            String homeTeam = details.getCompetitors().get(0).getName();
            String awayTeam = details.getCompetitors().get(1).getName();
            String homeAbbreviation = details.getCompetitors().get(0).getAbbreviation();
            String awayAbbreviation = details.getCompetitors().get(1).getAbbreviation();
            String homeScore = status.getHome_score();
            String awayScore = status.getAway_score();
            String matchStatus = status.getMatch_status();
            String statusString = status.getStatus();
            String startTime = details.getStart_time();
            List<PeriodScore> periodScores = status.getPeriod_scores();
            String competitionName = details.getSport_event_context().getCompetition().getName();
            String clockPlayed = status.getClock() != null ? status.getClock().getPlayed() : null;  //

            matches.add(new Match(homeTeam, awayTeam, homeAbbreviation, awayAbbreviation, homeScore, awayScore, matchStatus, statusString, startTime, periodScores, competitionName, clockPlayed));
        }

        return matches;

    }

    private void saveOrUpdateTeam(TeamResponse el) {
        Team team = teamRepository.findByCompetitorId(el.getId());
        if (team == null)
            team = new Team(el);
        else
            team.updateFromResponse(el);

        teamRepository.save(team);
    }

    private TeamsResponse mapTeamsJsonToObject(String jsonResponse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(jsonResponse, TeamsResponse.class);
    }

    public SeasonsResponse mapSeasonsJsonToObject(String jsonResponse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(jsonResponse, SeasonsResponse.class);
    }

    public List<TeamResponse> getTeamsByName(String teamName) {
        return teamRepository.findByNameIgnoreCaseContaining(teamName)
                .stream()
                .map(TeamResponse::new)
                .collect(Collectors.toList());
    }

    public UserFavoriteTeam addToFavorites(String email, String teamId) {
        AppUser user = userService.getByEmail(email);
        Team team = teamRepository.findByCompetitorId(teamId);
        UserFavoriteTeam uft = new UserFavoriteTeam(user, team);
        return userFavoriteTeamRepository.save(uft);
    }
}
