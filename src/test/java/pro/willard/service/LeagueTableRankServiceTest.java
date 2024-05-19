package pro.willard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.willard.dto.LeagueScoreDto;
import pro.willard.dto.TeamScoreDto;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeagueTableRankServiceTest {

    private LeagueTableRankService rankService;

    @BeforeEach
    void setUp() {
        rankService = new LeagueTableRankService();
    }

    @Test
    void testUpdateHomeTeamWin() {
        LeagueScoreDto leagueScoreDto = new LeagueScoreDto(new TeamScoreDto("HomeTeam", 3), new TeamScoreDto("AwayTeam", 1));

        rankService.update(leagueScoreDto);
        Map<String, Integer> updatedRankBoard = rankService.getRankBoard();

        assertEquals(3, updatedRankBoard.get("HomeTeam"));
        assertEquals(0, updatedRankBoard.get("AwayTeam"));
    }

    @Test
    void testUpdateHomeTeamLoses() {
        LeagueScoreDto leagueScoreDto = new LeagueScoreDto(new TeamScoreDto("HomeTeam", 1), new TeamScoreDto("AwayTeam", 3));

        rankService.update(leagueScoreDto);
        Map<String, Integer> updatedRankBoard = rankService.getRankBoard();

        assertEquals(0, updatedRankBoard.get("HomeTeam"));
        assertEquals(3, updatedRankBoard.get("AwayTeam"));
    }

    @Test
    void testUpdateTeamsDraw() {
        LeagueScoreDto leagueScoreDto = new LeagueScoreDto(new TeamScoreDto("HomeTeam", 1), new TeamScoreDto("AwayTeam", 1));

        rankService.update(leagueScoreDto);
        Map<String, Integer> updatedRankBoard = rankService.getRankBoard();

        assertEquals(1, updatedRankBoard.get("HomeTeam"));
        assertEquals(1, updatedRankBoard.get("AwayTeam"));
    }

    @Test
    void testUpdate() {
        List<LeagueScoreDto> scores = Arrays.asList(
                new LeagueScoreDto(new TeamScoreDto("Lions", 3), new TeamScoreDto("Snakes", 3)),
                new LeagueScoreDto(new TeamScoreDto("Tarantulas", 1), new TeamScoreDto("FC Awesome", 0)),
                new LeagueScoreDto(new TeamScoreDto("Lions", 1), new TeamScoreDto("FC Awesome", 1)),
                new LeagueScoreDto(new TeamScoreDto("Tarantulas", 3), new TeamScoreDto("Snakes", 1)),
                new LeagueScoreDto(new TeamScoreDto("Lions", 4), new TeamScoreDto("Grouches", 0))
        );

        rankService.update(scores);
        Map<String, Integer> rankBoard = rankService.getRankBoard();

        assertEquals(5, rankBoard.get("Lions"));
        assertEquals(1, rankBoard.get("Snakes"));
        assertEquals(6, rankBoard.get("Tarantulas"));
        assertEquals(1, rankBoard.get("FC Awesome"));
        assertEquals(0, rankBoard.get("Grouches"));
    }
}
