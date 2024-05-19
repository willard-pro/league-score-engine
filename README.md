# League Score Engine

Welcome to the League Score Engine!

## Overview

This application calculates the ranking of teams in a league based on their performance in matches. In this league, each match results in either a win, a loss, or a draw. A draw is worth 1 point, a win is worth 3 points, and a loss is worth 0 points. The teams are then ranked based on the total points they have accumulated.

In the event that two or more teams have the same number of points, they share the same rank and are printed in alphabetical order.

## Features

- Calculates the ranking of teams based on match results.
- Handles ties in points by sorting teams alphabetically.
- Provides clear and concise output of team rankings.

## How to Use

1. **Input Matches**: Input the results of each match including the teams involved and the outcome (win, loss, or draw).
2. **Calculate Rankings**: Run the application to calculate the rankings of teams based on the provided match results.
3. **View Results**: The application will display the rankings of teams, with tied teams listed alphabetically.

## Example

Suppose we have the following match results:

- Lions 3, Snakes 3 
- Tarantulas 1, FC Awesome 0
- Lions 1, FC Awesome 1
- Tarantulas 3, Snakes 1
- Lions 4, Grouches 0

The rankings would be:

1. Tarantulas, 6 pts
2. Lions, 5 pts
3. FC Awesome, 1 pt
3. Snakes, 1 pt
5. Grouches, 0 pts

## Technologies Used

This application is built using [Java 8](https://www.java.com/en/) and [Maven](https://maven.apache.org/).

## Building and Running the Application

Follow these steps to build and run the application from the command line interface (CLI):

### Logging

By default, __DEBUG__ logging is enabled, to disable this update [logback.xml](./src/main/resources/logback.xml)

### Build

1. Open your CLI.
2. Execute the following command to build the application:


    mvn clean install

### Run

1. After successfully building the application, navigate to the _target_ directory.
2. Execute the following command to run the application:


    java -jar lse-1.0-SNAPSHOT.jar

#### Arguments

If no arguments are specified, the application will request a user to enter the results of the matches on the CLI. 

1. --pretty, if specified it will print the rank board using ASCII art
2. --file=<filename>, if specified will parse the results from the provided file


## Feedback

Your feedback is valuable! If you encounter any issues or have suggestions for improvement, please feel free to [open an issue](https://github.com/your_username/league-ranking/issues) on GitHub.

Thank you for using the League Ranking Application!
