package com.sportradar.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StartMatchSteps {
    @Given("the scoreboard is empty")
    public void theScoreboardIsEmpty() {
        // TODO: Implement
    }

    @When("I start a new match between {string} and {string}")
    public void iStartANewMatchBetweenAnd(String homeTeam, String awayTeam) {
        // TODO: Implement
    }

    @Then("the scoreboard contains {int} match")
    public void theScoreboardContainsMatch(int matchCount) {
        // TODO: Implement
    }

    @Then("the match has a home team {string}")
    public void theMatchHasAHomeTeam(String homeTeam) {
        // TODO: Implement
    }

    @Then("the match has an away team {string}")
    public void theMatchHasAAwayTeam(String awayTeam) {
        // TODO: Implement
    }

    @Then("the score is {int} - {int}")
    public void theScoreIs(int homeScore, int awayScore) {
        // TODO: Implement
    }
}
