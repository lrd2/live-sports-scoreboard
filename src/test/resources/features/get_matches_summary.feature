Feature: Get a summary of matches in progress

  Scenario: Retrieve a summary of matches ordered by total score
    Given the scoreboard contains the following matches ordered by start time descending:
      | Home Team | Away Team | Home Score | Away Score |
      | Spain     | Mexico    | 1          | 2          |
      | Germany   | France    | 3          | 1          |
      | Italy     | Brazil    | 2          | 2          |
    When I retrieve the summary of matches
    Then the summary contains the following matches in order:
      | Home Team | Away Team | Home Score | Away Score |
      | Germany   | France    | 3          | 1          |
      | Italy     | Brazil    | 2          | 2          |
      | Spain     | Mexico    | 1          | 2          |

  Scenario: Retrieve a summary with matches having the same total score
    Given the scoreboard contains the following matches ordered by start time descending:
      | Home Team | Away Team | Home Score | Away Score |
      | Spain     | Mexico    | 2          | 2          |
      | Germany   | France    | 3          | 1          |
      | Italy     | Brazil    | 2          | 2          |
    When I retrieve the summary of matches
    Then the summary contains the following matches in order:
      | Home Team | Away Team | Home Score | Away Score |
      | Spain     | Mexico    | 2          | 2          |
      | Germany   | France    | 3          | 1          |
      | Italy     | Brazil    | 2          | 2          |

  Scenario: Retrieve a summary when no matches are in progress
    Given the scoreboard is empty
    When I retrieve the summary of matches
    Then the summary is empty