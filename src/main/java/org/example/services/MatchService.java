package org.example.services;

import org.example.controllers.dtos.MatchDto;
import org.example.models.Match;
import org.example.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public void registerMatch(MatchDto matchDto) {
        Match match = new Match();
        match.setSeason(matchDto.getSeason());
        match.setMatchDate(matchDto.getMatchDate());
        match.setHomeTeam(matchDto.getHomeTeam());
        match.setAwayTeam(matchDto.getAwayTeam());
        match.setHomeScore(matchDto.getHomeScore());
        match.setAwayScore(matchDto.getAwayScore());

        matchRepository.save(match);
    }

    public Map<String, Integer> getStandings(String season, LocalDate date) {
        List<Match> matches = matchRepository.findBySeasonAndMatchDateBefore(season, date);
        Map<String, Integer> standings = new TreeMap<>();

        for (Match match : matches) {
            String homeTeam = match.getHomeTeam();
            String awayTeam = match.getAwayTeam();

            standings.putIfAbsent(homeTeam, 0);
            standings.putIfAbsent(awayTeam, 0);

            if (match.getHomeScore() > match.getAwayScore()) {
                standings.put(homeTeam, standings.get(homeTeam) + 3);
            } else if (match.getHomeScore() < match.getAwayScore()) {
                standings.put(awayTeam, standings.get(awayTeam) + 3);
            } else {
                standings.put(homeTeam, standings.get(homeTeam) + 1);
                standings.put(awayTeam, standings.get(awayTeam) + 1);
            }
        }

        return standings;
    }

}
