package com.sportradar.steps;

import com.sportradar.LiveSportsScoreboard;
import io.cucumber.java.en.Given;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonSteps {

    private final LiveSportsScoreboard scoreboard;

    public CommonSteps(LiveSportsScoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    @Given("the scoreboard is empty")
    public void theScoreboardIsEmpty() {
        assertTrue(scoreboard.getMatches().isEmpty(), "The scoreboard should be empty");
    }

    public LiveSportsScoreboard getScoreboard() {
        return scoreboard;
    }

}
