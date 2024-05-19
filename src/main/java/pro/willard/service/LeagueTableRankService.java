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
        log.debug("Updating rankings of {}", leagueScoreDto);

        String homeTeam = leagueScoreDto.getHome().getName();
        String awayTeam = leagueScoreDto.getAway().getName();

        int homeTeamScore = leagueScoreDto.getHome().getScore();
        int awayTeamScore = leagueScoreDto.getAway().getScore();

        rankBoard.putIfAbsent(homeTeam, 0);
        rankBoard.putIfAbsent(awayTeam, 0);

        int homePoints = rankBoard.get(homeTeam);
        int awayPoints = rankBoard.get(awayTeam);

        if (homeTeamScore > awayTeamScore) {
            rankBoard.put(homeTeam, homePoints+3);
            log.debug("Team {} won and have {} points", homeTeam, rankBoard.get(homeTeam));
        } else if (homeTeamScore < awayTeamScore) {
            rankBoard.put(awayTeam, awayPoints+3);
            log.debug("Team {} won and have {} points", awayTeam, rankBoard.get(awayTeam));
        } else {
            rankBoard.put(homeTeam, homePoints+1);
            rankBoard.put(awayTeam, awayPoints+1);

            log.debug("Team {} and {} draw and have {} and {} points", homeTeam, awayTeam, rankBoard.get(homeTeam), rankBoard.get(awayTeam));
        }
    }

    public Map<String, Integer> getRankBoard() {
        return rankBoard;
    }
}
