import org.example.controllers.MatchController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.example.services.MatchService;
import org.example.models.Match;
import org.example.controllers.dtos.MatchDto;
import java.time.LocalDate;
import java.util.Map;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MatchControllerTest {

    @Mock
    private MatchService matchService;

    @InjectMocks
    private MatchController matchController;

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

        when(matchService.registerMatch(matchDto)).thenReturn(match);

        ResponseEntity<Match> response = matchController.registerMatch(matchDto);


        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Team A", response.getBody().getHomeTeam());
        verify(matchService, times(1)).registerMatch(matchDto);
    }

    @Test
    void testGetStandings() {
        LocalDate date = LocalDate.of(2023, 10, 4);
        Map<String, Map<String, Integer>> standings = Map.of(
                "standings", Map.of("Team A", 4, "Team B", 3, "Team C", 1),
                "gamesPlayed", Map.of("Team A", 2, "Team B", 2, "Team C", 2)
        );

        when(matchService.getStandings("2023", date)).thenReturn(standings);

        ResponseEntity<Map<String, Map<String, Integer>>> response = matchController.getStandings("2023", date);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(4, response.getBody().get("standings").get("Team A"));
        assertEquals(2, response.getBody().get("gamesPlayed").get("Team A"));
        verify(matchService, times(1)).getStandings("2023", date);
    }
}