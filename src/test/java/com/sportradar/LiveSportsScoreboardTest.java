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

    @Test
    public void shouldUpdateScore() {
        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, MEXICO);

        //when
        OperationResult result = liveSportsScoreboard.updateScore(SPAIN, MEXICO, 1, 2);

        //then
        Match updatedMatch = liveSportsScoreboard.getMatches().stream()
                .filter(match -> match.getHomeTeam().equals(SPAIN) && match.getAwayTeam().equals(MEXICO))
                .findFirst()
                .orElse(null);

        assertAll(
                () -> assertTrue(result.isSuccess(), "Score should be updated successfully"),
                () -> assertNotNull(updatedMatch, "The match should exist on the scoreboard"),
                () -> assertEquals(1, updatedMatch.getHomeScore(), "Home score is incorrect"),
                () -> assertEquals(2, updatedMatch.getAwayScore(), "Away score is incorrect")
        );
    }

    @Test
    public void shouldNotUpdateScoreIfMatchDoesNotExist() {

        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();

        //when
        OperationResult result = liveSportsScoreboard.updateScore(SPAIN, MEXICO, 1, 2);

        //then
        Match updatedMatch = liveSportsScoreboard.getMatches().stream()
                .filter(match -> match.getHomeTeam().equals(SPAIN) && match.getAwayTeam().equals(MEXICO))
                .findFirst()
                .orElse(null);
        assertAll(
                () -> assertNull(updatedMatch, "The match should not exist on the scoreboard"),
                () -> assertFalse(result.isSuccess(), "Score should not be updated if match does not exist")
        );

    }

    @Test
    public void shouldNotUpdateIfTeamNameIsNull() {

        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, MEXICO);

        //when
        OperationResult result = liveSportsScoreboard.updateScore(null, MEXICO, 1, 2);

        //then
        assertFalse(result.isSuccess(), "Score should not be updated if team name is null");

    }

    @Test
    public void shouldNotUpdateIfScoresAreNegative() {

        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, MEXICO);

        //when
        OperationResult result = liveSportsScoreboard.updateScore(SPAIN, MEXICO, -1, -2);

        //then
        Match updatedMatch = liveSportsScoreboard.getMatches().stream()
                .filter(match -> match.getHomeTeam().equals(SPAIN) && match.getAwayTeam().equals(MEXICO))
                .findFirst()
                .orElse(null);
        assertAll(
                () -> assertNotEquals(-1, updatedMatch.getHomeScore(), "Home score should not be negative"),
                () -> assertNotEquals(-2, updatedMatch.getAwayScore(), "Away score should not be negative"),
                () -> assertFalse(result.isSuccess(), "Score should not be updated if scores are negative")
        );

    }

    @Test
    public void shouldNotUpdateScoreIfTeamNamesAreInvalid() {
        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, MEXICO);

        //when
        OperationResult result = liveSportsScoreboard.updateScore("   ", MEXICO, 1, 2);

        //then
        Match updatedMatch = liveSportsScoreboard.getMatches().stream()
                .filter(match -> match.getHomeTeam().equals(SPAIN) && match.getAwayTeam().equals(MEXICO))
                .findFirst()
                .orElse(null);
        assertAll(
                () -> assertNotEquals(1, updatedMatch.getHomeScore(), "Home score should not be updated if team names are invalid"),
                () -> assertNotEquals(2, updatedMatch.getAwayScore(), "Away score should not be updated if team names are invalid"),
                () -> assertFalse(result.isSuccess(), "Score should not be updated if team names are invalid")
        );

    }

    @Test
    public void shouldUpdateScoreWithLargeValues() {
        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, MEXICO);

        //when
        OperationResult result = liveSportsScoreboard.updateScore(SPAIN, MEXICO, 1000, 2000);

        //then
        Match updatedMatch = liveSportsScoreboard.getMatches().stream()
                .filter(match -> match.getHomeTeam().equals(SPAIN) && match.getAwayTeam().equals(MEXICO))
                .findFirst()
                .orElse(null);
        assertAll(
                () -> assertTrue(result.isSuccess(), "Score should be updated successfully"),
                () -> assertEquals(1000, updatedMatch.getHomeScore(), "Home score should be updated"),
                () -> assertEquals(2000, updatedMatch.getAwayScore(), "Away score should be updated")
        );
    }

    @Test
    public void shouldUpdateScoreToZero() {
        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, MEXICO);

        //when
        OperationResult result = liveSportsScoreboard.updateScore(SPAIN, MEXICO, 0, 0);

        //then
        Match updatedMatch = liveSportsScoreboard.getMatches().stream()
                .filter(match -> match.getHomeTeam().equals(SPAIN) && match.getAwayTeam().equals(MEXICO))
                .findFirst()
                .orElse(null);

        assertAll(
                () -> assertTrue(result.isSuccess(), "Score should be updated successfully"),
                () -> assertEquals(0, updatedMatch.getHomeScore(), "Home score should be updated to 0"),
                () -> assertEquals(0, updatedMatch.getAwayScore(), "Away score should be updated to 0")
        );
    }

    @Test
    public void shouldNotModifyScoreboardIfMatchDoesNotExist() {
        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();

        //when
        int initialMatchCount = liveSportsScoreboard.getMatches().size();
        OperationResult result = liveSportsScoreboard.updateScore(SPAIN, MEXICO, 1, 2);

        //then
        assertAll(
                () -> assertFalse(result.isSuccess(), "Score should not be updated if match does not exist"),
                () -> assertEquals(initialMatchCount, liveSportsScoreboard.getMatches().size(), "Scoreboard state should not change")
        );
    }

    @Test
    public void shouldNotChangeScoreIfUpdateFails() {
        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, MEXICO);
        liveSportsScoreboard.updateScore(SPAIN, MEXICO, 1, 1);

        //when
        OperationResult result = liveSportsScoreboard.updateScore(SPAIN, MEXICO, -1, -1);

        //then
        Match match = liveSportsScoreboard.getMatches().stream()
                .filter(m -> m.getHomeTeam().equals(SPAIN) && m.getAwayTeam().equals(MEXICO))
                .findFirst()
                .orElse(null);

        assertAll(
                () -> assertFalse(result.isSuccess(), "Update should fail with negative scores"),
                () -> assertEquals(1, match.getHomeScore(), "Home score should remain unchanged"),
                () -> assertEquals(1, match.getAwayScore(), "Away score should remain unchanged")
        );
    }

}