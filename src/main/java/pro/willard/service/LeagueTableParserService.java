package pro.willard.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import pro.willard.dto.LeagueScoreDto;
import pro.willard.dto.TeamScoreDto;
import pro.willard.exception.LeagueException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class LeagueTableParserService {

    private static final Pattern PATTERN = Pattern.compile("(.+?) (\\d+), (.+?) (\\d+)");

    public List<LeagueScoreDto> parseFile(String filePath) throws LeagueException {
        log.debug("Parse file {}", filePath);

        List<LeagueScoreDto> leagueScoreDtos = new ArrayList<>();
        BufferedReader reader = null;

        int i=0;
        int failed=0;

        try {
            reader = new BufferedReader(new FileReader(filePath));

            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    leagueScoreDtos.add(parseLine(line));
                } catch(LeagueException e) {
                    failed++;
                    log.error("Failed to parse line {} of file {} due to {}", i+1, filePath, e.getMessage());
                }

                i++;
            }
        } catch (IOException e) {
            log.error("Failed to read file {} due to {}", filePath, e.getMessage());
        } finally {
            IOUtils.closeQuietly(reader);
        }

        if (failed > 0) {
            throw new LeagueException(String.format("%d failures detected in file %s", failed, filePath));
        }

        return leagueScoreDtos;
    }

    public LeagueScoreDto parseLine(String line) throws LeagueException {
        log.debug("Parse line {}", line);

        Matcher matcher = PATTERN.matcher(line);
        if (matcher.matches()) {
            // Extract team names and scores using regex groups
            String homeTeamName = matcher.group(1);
            int homeTeamScore = Integer.parseInt(matcher.group(2));
            String awayTeamName = matcher.group(3);
            int awayTeamScore = Integer.parseInt(matcher.group(4));

            log.debug("{} {} vs {} {}", homeTeamName, homeTeamScore, awayTeamName, awayTeamScore);

            return new LeagueScoreDto(new TeamScoreDto(homeTeamName, homeTeamScore), new TeamScoreDto(awayTeamName, awayTeamScore));
        } else {
            throw new LeagueException(String.format("Input '%s' does not match required pattern", line));
        }
    }
}
