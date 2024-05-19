package pro.willard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import pro.willard.dto.LeagueScoreDto;
import pro.willard.dto.TeamScoreDto;
import pro.willard.exception.LeagueException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeagueTableParserServiceTest {

    private LeagueTableParserService service;

    @BeforeEach
    public void setUp() {
        service = new LeagueTableParserService();
    }

    @Test
    void testParseFile(@TempDir Path tempDir) throws Exception {
        // Arrange
        String content = "Lions 3, Snakes 3\n" +
                "Tarantulas 1, FC Awesome 0\n" +
                "Lions 1, FC Awesome 1\n" +
                "Tarantulas 3, Snakes 1\n" +
                "Lions 4, Grouches 0\n";
        Path tempFile = tempDir.resolve("league_scores.txt");
        Files.write(tempFile, content.getBytes());

        // Act
        List<LeagueScoreDto> result;
        try {
            result = service.parseFile(tempFile.toString());
        } catch (LeagueException e) {
            fail("LeagueException was thrown: " + e.getMessage());
            return;
        }

        // Assert
        assertNotNull(result);
        assertEquals(5, result.size());

        assertLeagueScoreDto(result.get(0), "Lions", 3, "Snakes", 3);
        assertLeagueScoreDto(result.get(1), "Tarantulas", 1, "FC Awesome", 0);
        assertLeagueScoreDto(result.get(2), "Lions", 1, "FC Awesome", 1);
        assertLeagueScoreDto(result.get(3), "Tarantulas", 3, "Snakes", 1);
        assertLeagueScoreDto(result.get(4), "Lions", 4, "Grouches", 0);
    }

    private void assertLeagueScoreDto(LeagueScoreDto dto, String team1Name, int team1Score, String team2Name, int team2Score) {
        assertEquals(team1Name, dto.getHome().getName());
        assertEquals(team1Score, dto.getHome().getScore());
        assertEquals(team2Name, dto.getAway().getName());
        assertEquals(team2Score, dto.getAway().getScore());
    }

    @Test
    public void testParseLine_ValidInput() throws Exception {
        String line = "Lions 3, Snakes 3";
        LeagueScoreDto expected = new LeagueScoreDto(new TeamScoreDto("Lions", 3), new TeamScoreDto("Snakes", 3));

        LeagueScoreDto actual = service.parseLine(line);
        assertEquals(expected, actual);
    }

    @Test
    public void testParseLine_AnotherValidInput() throws Exception {
        String line = "Tarantulas 1, FC Awesome 0";
        LeagueScoreDto expected = new LeagueScoreDto(new TeamScoreDto("Tarantulas", 1), new TeamScoreDto("FC Awesome", 0));

        LeagueScoreDto actual = service.parseLine(line);
        assertEquals(expected, actual);
    }

    @Test
    public void testParseLine_InvalidInput() {
        String line = "Invalid input line";
        Exception exception = assertThrows(LeagueException.class, () -> {
            service.parseLine(line);
        });

        String expectedMessage = "Input 'Invalid input line' does not match required pattern";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testParseLine_PartialValidInput() {
        String line = "Lions 3, Snakes";
        Exception exception = assertThrows(Exception.class, () -> {
            service.parseLine(line);
        });

        String expectedMessage = "Input 'Lions 3, Snakes' does not match required pattern";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}