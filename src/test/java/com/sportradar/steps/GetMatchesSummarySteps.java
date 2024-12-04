package com.sportradar.steps;

import com.sportradar.LiveSportsScoreboard;
import com.sportradar.Match;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GetMatchesSummarySteps {

    private final LiveSportsScoreboard scoreboard;
    private List<Match> retrievedSummary;

    public GetMatchesSummarySteps(CommonSteps commonSteps) {
        this.scoreboard = commonSteps.getScoreboard();
        this.retrievedSummary = new ArrayList<>();
    }

    @Given("the scoreboard contains the following matches ordered by start time ascending")
    public void theScoreboardContainsTheFollowingMatchesOrderedByStartTimeDescending(List<Map<String, String>> matches) {
        matches.forEach(match -> {
            String homeTeam = match.get("Home Team");
            String awayTeam = match.get("Away Team");
            int homeScore = Integer.parseInt(match.get("Home Score"));
            int awayScore = Integer.parseInt(match.get("Away Score"));

            scoreboard.startMatch(homeTeam, awayTeam);
            scoreboard.updateScore(homeTeam, awayTeam, homeScore, awayScore);
        });
    }

    @When("I retrieve the summary of matches")
    public void iRetrieveTheSummaryOfMatches() {
        retrievedSummary = new ArrayList<>(scoreboard.getMatches());
    }

    @Then("the summary contains the following matches in order")
    public void theSummaryContainsTheFollowingMatchesInOrder(List<Map<String, String>> expectedMatches) {
        assertEquals(expectedMatches.size(), retrievedSummary.size(), "Summary size does not match the expected size.");

        for (int i = 0; i < expectedMatches.size(); i++) {
            Map<String, String> expectedMatch = expectedMatches.get(i);
            Match actualMatch = retrievedSummary.get(i);

            assertAll(
                    () -> assertEquals(expectedMatch.get("Home Team"), actualMatch.getHomeTeam(), "Home team mismatch"),
                    () -> assertEquals(expectedMatch.get("Away Team"), actualMatch.getAwayTeam(), "Away team mismatch"),
                    () -> assertEquals(Integer.parseInt(expectedMatch.get("Home Score")), actualMatch.getHomeScore(), "Home score mismatch"),
                    () -> assertEquals(Integer.parseInt(expectedMatch.get("Away Score")), actualMatch.getAwayScore(), "Away score mismatch")
            );
        }
    }

    @Then("the summary is empty")
    public void theSummaryIsEmpty() {
        assertTrue(retrievedSummary.isEmpty(), "The summary should be empty.");
    }
}