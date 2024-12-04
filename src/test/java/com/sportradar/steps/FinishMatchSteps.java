package com.sportradar.steps;

import com.sportradar.LiveSportsScoreboard;
import com.sportradar.Match;
import com.sportradar.OperationResult;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FinishMatchSteps {

    private final LiveSportsScoreboard scoreboard;
    private OperationResult finishResult;

    public FinishMatchSteps(CommonSteps commonSteps) {
        this.scoreboard = commonSteps.getScoreboard();
    }

    @Given("the scoreboard contains the following matches:")
    public void theScoreboardContainsTheFollowingMatches(List<Map<String, String>> matches) {
        matches.forEach(match -> {
            String homeTeam = match.get("Home Team");
            String awayTeam = match.get("Away Team");
            scoreboard.startMatch(homeTeam, awayTeam);
        });
    }

    @When("I finish the match between {string} and {string}")
    public void iFinishTheMatchBetweenAnd(String homeTeam, String awayTeam) {
        if ("null".equals(homeTeam)) {
            homeTeam = null;
        }
        if ("null".equals(awayTeam)) {
            awayTeam = null;
        }
        finishResult = scoreboard.finishMatch(homeTeam, awayTeam);
    }

    @Then("the match between {string} and {string} is no longer on the scoreboard")
    public void theMatchBetweenAndIsNoLongerOnTheScoreboard(String homeTeam, String awayTeam) {
        Match match = scoreboard.getMatches().stream()
                .filter(m -> m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam))
                .findFirst()
                .orElse(null);
        assertNull(match, "The match should be removed from the scoreboard");
    }

    @Then("the match between {string} and {string} is still on the scoreboard")
    public void theMatchBetweenAndIsStillOnTheScoreboard(String homeTeam, String awayTeam) {
        Match match = scoreboard.getMatches().stream()
                .filter(m -> m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam))
                .findFirst()
                .orElse(null);
        assertNotNull(match, "The match should still be on the scoreboard");
    }

    @Then("the finish operation fails with the message {string}")
    public void theFinishOperationFailsWithTheMessage(String expectedMessage) {
        assertFalse(finishResult.isSuccess(), "The finish operation should fail");
        assertEquals(expectedMessage, finishResult.getMessage(), "The failure message is incorrect");
    }

}
