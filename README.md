# Live Sports Scoreboard

## Overview

This project is a library implementation of a live sports scoreboard. It allows managing and tracking live matches,
updating scores, and retrieving a summary of ongoing matches. The library is designed to be simple and efficient.

## Features

- Start a new match with initial scores (0–0).
- Update the score of an ongoing match.
- Finish a match and remove it from the scoreboard.
- Retrieve a summary of all ongoing matches, sorted by:
    1. Total score (descending).
    2. Time of match creation (recent matches first for ties).

## Design limitations

Given the timeframe and goal of the project, the design is kept simple and focused on the core requirements.
- The solution is good for managing scoreboards for some team sports, such as football or basketball, etc. However, the current
  implementation is focused on specifics of football and FIFA World Cup.:
    1. Each match has two teams, one of which is home and the other is away.
    2. Each team has a unique name representing a country, but clubs are supported as long as they have unique names and there is no further specifics like leagues or divisions.
- The solution is not suitable for sports which score is presented in a different way than total number of points, such as tennis or volleyball.
- The solution is not designed for concurrent access. It assumes a single-threaded environment.
- The solution is optimized for a limited number of active matches and may require refactoring to handle a significantly larger dataset efficiently.
- The solution is explicitly designed to use in-memory storage, as stated in the task requirements. Introducing a database would add unnecessary complexity and overhead, contradicting the principle of simplicity (KISS) and the scope of this project.

## Potential extensions

Due to the simplicity of the current design, there are several potential extensions that could be considered:
- Support for more sports with different scoring systems.
- Support for concurrent access and scalability.
- Support for persistence using a database or other storage solutions.
- Support for more detailed match information, such as match duration, player statistics, etc.
- Support for more complex match management, such as pausing, resuming, or canceling matches.

## Technical Details

- **Language:** Java
- **Build Tool:** Maven
- **Testing Approach:** Test-Driven Development (TDD) with focus on ATDD.
- **Key Principles:** Clean Code, SOLID, ATDD, KISS, YAGNI.
- **Limitations:** 
    1. There is no extended polymorphism or complex inheritance which would enable introduction of sports which specifics differ significantly from football, as it would be overkill for the current scope.
    2. The scoreboard uses in-memory data structures (e.g., lists) to store match information, as specified in the project requirements. This approach is sufficient for the current scope and ensures quick access and updates without the need for external dependencies or configuration. However, it may not be the most efficient solution for a large number of matches.
    3. Acceptance tests are implemented using Cucumber, ensuring the functionality meets business requirements in a natural language format.

## Assumptions

- Scores cannot be negative.
- Teams must have unique names (no duplicate matches between the same teams).
- Summary sorting adheres strictly to the order specified in the requirements.

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/lrd2/live-sports-scoreboard.git
