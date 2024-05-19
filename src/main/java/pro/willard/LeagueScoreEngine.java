package pro.willard;

import pro.willard.dto.LeagueScoreDto;
import pro.willard.exception.LeagueException;
import pro.willard.service.LeagueTableParserService;
import pro.willard.service.LeagueTableRankService;

import java.io.IOException;
import java.util.*;

/**
 * Hello world!
 *
 */
public class LeagueScoreEngine {

    private static final LeagueTableRankService rankService = new LeagueTableRankService();
    private static final LeagueTableParserService parserService = new LeagueTableParserService();

    public static void main( String[] args ) throws IOException, LeagueException {
        List<String> argsList = Arrays.asList(args);
        boolean prettyPrint = argsList.contains("--pretty");

        Optional<String> argFile = argsList.stream().filter(arg -> arg.startsWith("--file")).findFirst();
        if (argFile.isPresent()) {
            readFile(argFile.get().substring(7), prettyPrint);
        } else {
            readCli(prettyPrint);
        }
    }

    private static void readCli(boolean prettyPrint) throws IOException {
        List<LeagueScoreDto> leagueScoreDtos = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your input in the format of '<Home Team Name> <Home Team Score>, <Away Team Name> <Away Team Score>'. Type 'done' to finish.");

        String line;
        while (!(line = scanner.nextLine()).equals("done")) {
            try {
                leagueScoreDtos.add(parserService.parseLine(line));
            } catch (LeagueException e) {
                System.out.print(e.getMessage()+"\n");
            }
        }
        scanner.close();

        rankService.update(leagueScoreDtos);
        System.out.println(rankService.printRankBoard(prettyPrint));
    }

    private static void readFile(String filename, boolean prettyPrint) throws IOException, LeagueException {
        List<LeagueScoreDto> leagueScoreDtos = parserService.parseFile(filename);
        rankService.update(leagueScoreDtos);

        System.out.println(rankService.printRankBoard(prettyPrint));
    }

    private static void printFailureAndUsage(String message) {
        System.out.println("Failure: " + message);
        System.out.println("Usage: java Main --file=<filename>");
        System.out.println("\t --file: file containing the scores of the matches");
        System.out.println("\t --pretty: if specified, will print a pretty rank board");
    }
}
