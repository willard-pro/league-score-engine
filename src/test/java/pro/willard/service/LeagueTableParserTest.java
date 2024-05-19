package pro.willard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.willard.dto.LeagueScoreDto;
import pro.willard.dto.TeamScoreDto;
import pro.willard.exception.LeagueException;
import pro.willard.service.LeagueTableParser;

import static org.junit.jupiter.api.Assertions.*;

class LeagueTableParserTest {

    private LeagueTableParser parser;

    @BeforeEach
    public void setUp() {
        parser = new LeagueTableParser();
    }

    @Test
    public void testParseLine_ValidInput() throws Exception {
        String line = "Lions 3, Snakes 3";
        LeagueScoreDto expected = new LeagueScoreDto(new TeamScoreDto("Lions", 3), new TeamScoreDto("Snakes", 3));

        LeagueScoreDto actual = parser.parseLine(line);
        assertEquals(expected, actual);
    }

    @Test
    public void testParseLine_AnotherValidInput() throws Exception {
        String line = "Tarantulas 1, FC Awesome 0";
        LeagueScoreDto expected = new LeagueScoreDto(new TeamScoreDto("Tarantulas", 1), new TeamScoreDto("FC Awesome", 0));

        LeagueScoreDto actual = parser.parseLine(line);
        assertEquals(expected, actual);
    }

    @Test
    public void testParseLine_InvalidInput() {
        String line = "Invalid input line";
        Exception exception = assertThrows(LeagueException.class, () -> {
            parser.parseLine(line);
        });

        String expectedMessage = "Input 'Invalid input line' does not match required pattern";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testParseLine_PartialValidInput() {
        String line = "Lions 3, Snakes";
        Exception exception = assertThrows(Exception.class, () -> {
            parser.parseLine(line);
        });

        String expectedMessage = "Input 'Lions 3, Snakes' does not match required pattern";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}