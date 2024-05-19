package pro.willard.service;

import lombok.extern.slf4j.Slf4j;
import pro.willard.dto.LeagueScoreDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class LeagueTableRankService {

    private final Map<String, Integer> rankBoard = new HashMap<>();

    public void update(List<LeagueScoreDto> leagueScoreDto) {
        leagueScoreDto.forEach(this::update);
    }

    public void update(LeagueScoreDto leagueScoreDto) {
        String homeTeam = leagueScoreDto.getHome().getName();
        String awayTeam = leagueScoreDto.getAway().getName();

        int homeTeamScore = leagueScoreDto.getHome().getScore();
        int awayTeamScore = leagueScoreDto.getAway().getScore();

        int homePoints = rankBoard.putIfAbsent(homeTeam, 0);
        int awayPoints = rankBoard.putIfAbsent(awayTeam, 0);

        if (homeTeamScore > awayTeamScore) {
            rankBoard.put(homeTeam, homePoints+3);
        } else if (homeTeamScore < awayTeamScore) {
            rankBoard.put(awayTeam, awayPoints+3);
        } else {
            rankBoard.put(homeTeam, homePoints+1);
            rankBoard.put(awayTeam, awayPoints+1);
        }
    }

    public Map<String, Integer> getRankBoard() {
        return rankBoard;
    }

    public void display() {

    }
}
