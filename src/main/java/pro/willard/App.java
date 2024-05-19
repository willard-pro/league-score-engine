package pro.willard;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) {
        if (args.length == 0) {

        } else if (args.length == 1) {
            String fileName = null;

            for (String arg : args) {
                if (arg.startsWith("--file=")) {
                    fileName = arg.substring(7);
                }
            }
        } else {
            printFailureAndUsage("Invalid arguments");
        }
    }

    private static void printFailureAndUsage(String message) {
        System.out.println("Failure: " + message);
        System.out.println("Usage: java Main --file=<filename>");
    }
}
