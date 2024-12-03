# Live Sports Scoreboard

## Overview
This project is a library implementation of a live sports scoreboard. It allows managing and tracking live matches, updating scores, and retrieving a summary of ongoing matches. The library is designed to be simple, efficient, and easily extensible.

## Features
- Start a new match with initial scores (0–0).
- Update the score of an ongoing match.
- Finish a match and remove it from the scoreboard.
- Retrieve a summary of all ongoing matches, sorted by:
    1. Total score (descending).
    2. Time of match creation (recent matches first for ties).

## Technical Details
- **Language:** Java
- **Build Tool:** Maven
- **Testing Approach:** Test-Driven Development (TDD) with focus on ATDD.
- **Key Principles:** Clean Code, SOLID, ATDD.

## Assumptions
- Scores cannot be negative.
- Teams must have unique names (no duplicate matches between the same teams).
- Summary sorting adheres strictly to the order specified in the requirements.

## How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/lrd2/live-sports-scoreboard.git
