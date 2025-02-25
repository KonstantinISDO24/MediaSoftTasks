package org.example.repository;

import org.example.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findBySeasonAndMatchDateBefore(String season, LocalDate date);
}
