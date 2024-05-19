package pro.willard.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import pro.willard.dto.LeagueScoreDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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

    public String printRankBoard(boolean pretty) throws IOException {
        StringBuffer buffer = new StringBuffer();

        if (pretty) {
            ClassLoader classLoader = getClass().getClassLoader();

            InputStream inputStream = null;
            try {
                inputStream = classLoader.getResourceAsStream("banner.txt");
                buffer.append(IOUtils.toString(inputStream));
                buffer.append("╔════╦════════════════════════╦═════╗\n");
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        }

        // Sort the rank board by value (points)
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(rankBoard.entrySet());

        sortedEntries.sort((e1, e2) -> {
            // Compare points first
            int compare = e2.getValue().compareTo(e1.getValue());
            if (compare != 0) {
                return compare;
            }

            // If points are equal, compare team names
            return e1.getKey().compareTo(e2.getKey());
        });

        int rank = 1;
        int previousPoints = Integer.MAX_VALUE; // Initialize to a large value

        for (Map.Entry<String, Integer> entry : sortedEntries) {
            String teamName = entry.getKey();
            int points = entry.getValue();

            // Check if the points are different from the previous entry
            if (points != previousPoints) {
                // Update the rank if points are different
                rank = sortedEntries.indexOf(entry) + 1;
            }

            // Print the rank, team name, and points in the desired format
            if (pretty) {
                buffer.append(String.format("║ %-2d ║ %-22s ║ %3d ║\n", rank, teamName, points));
            } else {
                buffer.append(rank + ". " + teamName + ", " + points + " pts\n");
            }

            // Update the previous points value
            previousPoints = points;
        }

        if (pretty) {
            buffer.append("╚════╩════════════════════════╩═════╝");
        }

        return buffer.toString();
    }
}
