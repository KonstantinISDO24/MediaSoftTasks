package org.example.controllers.dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MatchDto {
    private String season;
    private LocalDate matchDate;
    private String homeTeam;
    private String awayTeam;
    private int homeScore;
    private int awayScore;
}