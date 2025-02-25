import org.example.services.MatchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.repository.MatchRepository;
import org.example.models.Match;
import org.example.controllers.dtos.MatchDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchService matchService;

    @Test
    void testRegisterMatch() {
        MatchDto matchDto = new MatchDto();
        matchDto.setSeason("2023");
        matchDto.setMatchDate(LocalDate.of(2023, 10, 1));
        matchDto.setHomeTeam("Team A");
        matchDto.setAwayTeam("Team B");
        matchDto.setHomeScore(2);
        matchDto.setAwayScore(1);

        Match match = new Match();
        match.setSeason("2023");
        match.setMatchDate(LocalDate.of(2023, 10, 1));
        match.setHomeTeam("Team A");
        match.setAwayTeam("Team B");
        match.setHomeScore(2);
        match.setAwayScore(1);

        when(matchRepository.save(any(Match.class))).thenReturn(match);

        Match result = matchService.registerMatch(matchDto);

        assertNotNull(result);
        assertEquals("Team A", result.getHomeTeam());
        assertEquals("Team B", result.getAwayTeam());
        verify(matchRepository, times(1)).save(any(Match.class));
    }

    @Test
    void testGetStandings() {
        LocalDate date = LocalDate.of(2023, 10, 4);
        Match match1 = new Match();
        match1.setSeason("2023");
        match1.setMatchDate(LocalDate.of(2023, 10, 1));
        match1.setHomeTeam("Team A");
        match1.setAwayTeam("Team B");
        match1.setHomeScore(2);
        match1.setAwayScore(1);

        Match match2 = new Match();
        match2.setSeason("2023");
        match2.setMatchDate(LocalDate.of(2023, 10, 2));
        match2.setHomeTeam("Team A");
        match2.setAwayTeam("Team C");
        match2.setHomeScore(1);
        match2.setAwayScore(1);

        when(matchRepository.findBySeasonAndMatchDateBefore("2023", date))
                .thenReturn(List.of(match1, match2));

        Map<String, Map<String, Integer>> result = matchService.getStandings("2023", date);

        assertNotNull(result);
        assertEquals(4, result.get("standings").get("Team A"));
        assertEquals(0, result.get("standings").get("Team B"));
        assertEquals(1, result.get("standings").get("Team C"));
        assertEquals(2, result.get("gamesPlayed").get("Team A"));
        assertEquals(1, result.get("gamesPlayed").get("Team B"));
        assertEquals(1, result.get("gamesPlayed").get("Team C"));
    }
}