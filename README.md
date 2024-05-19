# League Ranking Application

Welcome to the League Ranking Application!

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

### Build

1. Open your CLI.
2. Navigate to the root directory of the application.
3. Execute the following command to build the application:


    mvn clean install

### Run

1. After successfully building the application, navigate to the target directory.
2. Execute the following command to run the application:


    java -jar <application-name>.jar


## Feedback

Your feedback is valuable! If you encounter any issues or have suggestions for improvement, please feel free to [open an issue](https://github.com/your_username/league-ranking/issues) on GitHub.

Thank you for using the League Ranking Application!
