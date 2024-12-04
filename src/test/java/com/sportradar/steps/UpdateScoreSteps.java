package com.sportradar.steps;

import com.sportradar.LiveSportsScoreboard;
import com.sportradar.OperationResult;
import com.sportradar.Match;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateScoreSteps {

    private final LiveSportsScoreboard scoreboard;

    private OperationResult updateResult;
    private Match updatedMatch;

    public UpdateScoreSteps(CommonSteps commonSteps) {
        this.scoreboard = commonSteps.getScoreboard();
    }

    @Given("the scoreboard contains a match between {string} and {string}")
    public void theScoreboardContainsAMatchBetween(String homeTeam, String awayTeam) {
        scoreboard.startMatch(homeTeam, awayTeam);
    }

    @When("I update the score to {int} - {int} for the match between {string} and {string}")
    public void iUpdateTheScoreToForTheMatchBetween(int homeScore, int awayScore, String homeTeam, String awayTeam) {
        updateResult = scoreboard.updateScore(homeTeam, awayTeam, homeScore, awayScore);
        updatedMatch = scoreboard.getMatches().stream()
                .filter(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam))
                .findFirst()
                .orElse(null);
    }

    @Then("the match between {string} and {string} has a score of {int} - {int}")
    public void theMatchBetweenAndHasAScoreOf(String homeTeam, String awayTeam, int expectedHomeScore, int expectedAwayScore) {
        assertNotNull(updatedMatch, "The match should exist on the scoreboard");
        assertAll(
                () -> assertEquals(expectedHomeScore, updatedMatch.getHomeScore(), "Home score is incorrect"),
                () -> assertEquals(expectedAwayScore, updatedMatch.getAwayScore(), "Away score is incorrect")
        );
    }

    @Then("the update fails with the message {string}")
    public void theUpdateFailsWithTheMessage(String expectedMessage) {
        assertFalse(updateResult.isSuccess(), "The update should fail");
        assertEquals(expectedMessage, updateResult.getMessage(), "The failure message is incorrect");
    }
}