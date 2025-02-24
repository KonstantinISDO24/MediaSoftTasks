package org.example.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "season")
    private String season;

    @Column(name = "match_date")
    private LocalDate matchDate;

    @Column(name = "home_team")
    private String homeTeam;

    @Column(name = "away_team")
    private String awayTeam;

    @Column(name = "home_Score")
    private int homeScore;

    @Column(name = "away_Score")
    private int awayScore;
}
