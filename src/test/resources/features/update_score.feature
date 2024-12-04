Feature: Update the score of a match

  Scenario: Successfully updating the score of a match
    Given the scoreboard contains a match between "Spain" and "Mexico"
    When I update the score to 2 - 3 for the match between "Spain" and "Mexico"
    Then the match between "Spain" and "Mexico" has a score of 2 - 3

  Scenario: Failing to update the score for a non-existing match
    Given the scoreboard is empty
    When I update the score to 1 - 0 for the match between "Spain" and "Mexico"
    Then the update fails with the message "Match does not exist."

  Scenario: Failing to update the score with invalid values
    Given the scoreboard contains a match between "Spain" and "Mexico"
    When I update the score to -1 - 2 for the match between "Spain" and "Mexico"
    Then the update fails with the message "Scores must be non-negative."

  Scenario: Successfully updating the score to zero
    Given the scoreboard contains a match between "Spain" and "Mexico"
    When I update the score to 0 - 0 for the match between "Spain" and "Mexico"
    Then the match between "Spain" and "Mexico" has a score of 0 - 0