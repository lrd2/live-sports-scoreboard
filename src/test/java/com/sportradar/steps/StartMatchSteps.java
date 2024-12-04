package com.sportradar.steps;

import com.sportradar.LiveSportsScoreboard;
import com.sportradar.Match;
import com.sportradar.OperationResult;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StartMatchSteps {

    private final LiveSportsScoreboard scoreboard;
    private Match startedMatch;

    public StartMatchSteps(CommonSteps commonSteps) {
        this.scoreboard = commonSteps.getScoreboard();
    }

    @When("I start a new match between {string} and {string}")
    public void iStartANewMatchBetweenAnd(String homeTeam, String awayTeam) {
        OperationResult result = scoreboard.startMatch(homeTeam, awayTeam);
        startedMatch = scoreboard.getMatches().stream()
                .filter(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam))
                .findFirst()
                .orElse(null);
        assertAll(
                () -> assertTrue(result.isSuccess()),
                () -> assertNotNull(startedMatch, "Match should be added to the scoreboard")
        );
    }

    @Then("the scoreboard contains {int} match")
    public void theScoreboardContainsMatch(int matchCount) {
        assertEquals(matchCount, scoreboard.getMatches().size(), "The number of matches in the scoreboard is incorrect");
    }

    @Then("the match has a home team {string}")
    public void theMatchHasAHomeTeam(String homeTeam) {
        assertEquals(homeTeam, startedMatch.getHomeTeam(), "The home team is incorrect");
    }

    @Then("the match has an away team {string}")
    public void theMatchHasAAwayTeam(String awayTeam) {
        assertEquals(awayTeam, startedMatch.getAwayTeam(), "The away team is incorrect");
    }

    @Then("the score is {int} - {int}")
    public void theScoreIs(int homeScore, int awayScore) {
        assertAll(
                () -> assertEquals(homeScore, startedMatch.getHomeScore(), "The home score is incorrect"),
                () -> assertEquals(awayScore, startedMatch.getAwayScore(), "The away score is incorrect")
        );
    }
}
