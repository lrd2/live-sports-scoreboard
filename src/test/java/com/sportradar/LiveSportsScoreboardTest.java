package com.sportradar;

import org.junit.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LiveSportsScoreboardTest {

    private static final String SPAIN = "Spain";
    private static final String MEXICO = "Mexico";
    private static final String SWEDEN = "Sweden";
    private static final String SPAIN_UPPER_CASE = "SPAIN";

    @Test
    public void shouldStartMatch() {
        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();

        //when
        OperationResult result = liveSportsScoreboard.startMatch(SPAIN, MEXICO);

        //then
        assertTrue(result.isSuccess(), "Match should be started successfully");
    }

    @Test
    public void shouldNotStartMatchIfTeamIsPlaying() {
        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, MEXICO);

        //when
        OperationResult result = liveSportsScoreboard.startMatch(SPAIN, SWEDEN);

        //then
        assertFalse(result.isSuccess(), "Match should not be started if one of the teams is already playing");
    }

    @Test
    public void shouldNotStartMatchIfTeamNameIsNull() {
        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();

        //when
        OperationResult result = liveSportsScoreboard.startMatch(null, SWEDEN);

        //then
        assertFalse(result.isSuccess(), "Match should not be started if team name is null");
    }

    @Test
    public void shouldNotStartMatchIfThrowsException() {
        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();

        //when
        OperationResult result = liveSportsScoreboard.startMatch(" ", SWEDEN);

        //then
        assertFalse(result.isSuccess(), "Match should not be started if exception is thrown");
    }

    @Test
    public void shouldGetMatches() {
        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, MEXICO);

        //when
        Set<Match> matches = liveSportsScoreboard.getMatches();

        //then
        assertEquals(1, matches.size(), "The number of matches in the scoreboard is incorrect");
    }

    @Test
    public void shouldNotReturnMatchIfScoreboardIsEmpty() {
        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();

        //when
        Set<Match> matches = liveSportsScoreboard.getMatches();

        //then
        assertTrue(matches.isEmpty(), "The scoreboard should be empty");
    }

    @Test
    public void shouldNotReturnMatchIfItWasNotAdded() {
        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, " ");

        //when
        Set<Match> matches = liveSportsScoreboard.getMatches();

        //then
        assertTrue(matches.isEmpty(), "The match should not be in the list of matches");
    }

    @Test
    public void shouldAddMatchIfTeamIsNotPlaying() {
        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, SPAIN_UPPER_CASE);

        //when
        OperationResult result = liveSportsScoreboard.startMatch(SPAIN, MEXICO);

        //then
        assertTrue(result.isSuccess(), "Match should be added if team is not playing");
    }

}