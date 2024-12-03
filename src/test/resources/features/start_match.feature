Feature: Start a new match

  Scenario: Successfully starting a match
    Given the scoreboard is empty
    When I start a new match between "Mexico" and "Canada"
    Then the scoreboard contains 1 match
    And the match has a home team "Mexico"
    And the match has an away team "Canada"
    And the score is 0 - 0