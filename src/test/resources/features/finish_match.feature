Feature: Finish a match currently in progress

  Scenario: Successfully finishing a match
    Given the scoreboard contains a match between "Spain" and "Mexico"
    When I finish the match between "Spain" and "Mexico"
    Then the match between "Spain" and "Mexico" is no longer on the scoreboard

  Scenario: Failing to finish a match that does not exist
    Given the scoreboard is empty
    When I finish the match between "Spain" and "Mexico"
    Then the finish operation fails with the message "Match does not exist."

  Scenario: Failing to finish a match with invalid team names
    Given the scoreboard contains a match between "Spain" and "Mexico"
    When I finish the match between "" and "Mexico"
    Then the finish operation fails with the message "Team names cannot be empty."

  Scenario: Successfully finishing one of multiple matches
    Given the scoreboard contains the following matches:
      | Home Team | Away Team |
      | Spain     | Mexico    |
      | Sweden    | Portugal  |
    When I finish the match between "Spain" and "Mexico"
    Then the match between "Spain" and "Mexico" is no longer on the scoreboard
    And the match between "Sweden" and "Portugal" is still on the scoreboard

  Scenario: Failing to finish a match with null team names
    Given the scoreboard contains a match between "Spain" and "Mexico"
    When I finish the match between "null" and "Mexico"
    Then the finish operation fails with the message "Team names cannot be null."