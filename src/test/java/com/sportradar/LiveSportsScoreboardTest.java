package com.sportradar;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LiveSportsScoreboardTest {

    private static final String SPAIN = "Spain";
    private static final String MEXICO = "Mexico";
    private static final String SWEDEN = "Sweden";
    private static final String SPAIN_UPPER_CASE = "SPAIN";
    private static final String PORTUGAL = "Portugal";
    private static final String GERMANY = "Germany";
    private static final String AUSTRIA = "Austria";

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
        List<Match> matches = liveSportsScoreboard.getMatches();

        //then
        assertEquals(1, matches.size(), "The number of matches in the scoreboard is incorrect");
    }

    @Test
    public void shouldGetMatchesWithEmptyResult() {

        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();

        //when
        List<Match> matches = liveSportsScoreboard.getMatches();

        //then
        assertTrue(matches.isEmpty(), "The scoreboard should be empty");

    }

    @Test
    public void shouldGetMatchesOrderedByTotalScoreFirst() {

        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, MEXICO);
        liveSportsScoreboard.startMatch(SWEDEN, PORTUGAL);
        liveSportsScoreboard.updateScore(SPAIN, MEXICO, 3, 2);
        liveSportsScoreboard.updateScore(SWEDEN, PORTUGAL, 1, 1);

        //when
        List<Match> matches = liveSportsScoreboard.getMatches();

        //then
        assertAll(
                () -> assertEquals(2, matches.size(), "The number of matches in the scoreboard is incorrect"),
                () -> assertEquals(5, matches.get(0).getTotalScore(), "The first match should have the highest total score"),
                () -> assertEquals(2, matches.get(1).getTotalScore(), "The second match should have the second highest total score")
        );

    }

    @Test
    public void shouldGetMatchesOrderedByStartTimeDescWhenScoresAreEqual() {

        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, MEXICO);
        liveSportsScoreboard.startMatch(GERMANY, AUSTRIA);
        liveSportsScoreboard.startMatch(SWEDEN, PORTUGAL);
        liveSportsScoreboard.updateScore(GERMANY, AUSTRIA, 2, 2);

        //when
        List<Match> matches = liveSportsScoreboard.getMatches();

        //then
        assertAll(
                () -> assertEquals(3, matches.size(), "The number of matches in the scoreboard is incorrect"),
                () -> assertEquals(GERMANY, matches.get(0).getHomeTeam(), "The first match should have the highest score"),
                () -> assertEquals(SWEDEN, matches.get(1).getHomeTeam(), "The second match should have the most recent start time"),
                () -> assertEquals(SPAIN, matches.get(2).getHomeTeam(), "The last match should have the oldest start time")
        );

    }

    @Test
    public void shouldNotReturnMatchIfScoreboardIsEmpty() {
        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();

        //when
        List<Match> matches = liveSportsScoreboard.getMatches();

        //then
        assertTrue(matches.isEmpty(), "The scoreboard should be empty");
    }

    @Test
    public void shouldNotReturnMatchIfItWasNotAdded() {
        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, " ");

        //when
        List<Match> matches = liveSportsScoreboard.getMatches();

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

    @Test
    public void shouldFinishMatch() {

        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, MEXICO);

        //when
        OperationResult result = liveSportsScoreboard.finishMatch(SPAIN, MEXICO);

        //then
        assertTrue(result.isSuccess(), "Match should be finished successfully");

    }

    @Test
    public void shouldNotFinishMatchIfMatchDoesNotExist() {

        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();

        //when
        OperationResult result = liveSportsScoreboard.finishMatch(SPAIN, MEXICO);

        //then
        assertFalse(result.isSuccess(), "Match should not be finished if it does not exist");

    }

    @Test
    public void shouldNotFinishMatchIfTeamNameIsInvalid() {

        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, MEXICO);

        //when
        OperationResult result = liveSportsScoreboard.finishMatch("   ", MEXICO);

        //then
        assertFalse(result.isSuccess(), "Match should not be finished if team name is invalid");

    }

    @Test
    public void shouldNotFinishMatchIfTeamNameIsNull() {

        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, MEXICO);

        //when
        OperationResult result = liveSportsScoreboard.finishMatch(null, MEXICO);

        //then
        assertFalse(result.isSuccess(), "Match should not be finished if team name is null");

    }

    @Test
    public void shouldFinishOnlyOneMatch() {

        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, MEXICO);
        liveSportsScoreboard.startMatch(SWEDEN, PORTUGAL);

        //when
        OperationResult result = liveSportsScoreboard.finishMatch(SPAIN, MEXICO);

        //then
        Match finishedMatch = liveSportsScoreboard.getMatches().stream()
                .filter(match -> match.getHomeTeam().equals(SPAIN) && match.getAwayTeam().equals(MEXICO))
                .findFirst()
                .orElse(null);
        Match existingMatch = liveSportsScoreboard.getMatches().stream()
                .filter(match -> match.getHomeTeam().equals(SWEDEN) && match.getAwayTeam().equals(PORTUGAL))
                .findFirst()
                .orElse(null);
        assertAll(
                () -> assertTrue(result.isSuccess(), "Match should be finished successfully"),
                () -> assertNull(finishedMatch, "The match should not be on the scoreboard"),
                () -> assertNotNull(existingMatch, "The other match should still be on the scoreboard")
        );
    }

    @Test
    public void shouldNotFinishMatchIfHomeTeamNameIsMixedWithAwayTeamName() {

        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, MEXICO);

        //when
        OperationResult result = liveSportsScoreboard.finishMatch(MEXICO, SPAIN);

        //then
        assertFalse(result.isSuccess(), "Match should not be finished if home and away team names are mixed.");

    }

    @Test
    public void shouldClearScoreboardAfterFinishingLastMatch() {

        //given
        LiveSportsScoreboard liveSportsScoreboard = new LiveSportsScoreboard();
        liveSportsScoreboard.startMatch(SPAIN, MEXICO);

        //when
        liveSportsScoreboard.finishMatch(SPAIN, MEXICO);

        //then
        assertTrue(liveSportsScoreboard.getMatches().isEmpty(), "The scoreboard should be empty after finishing the last match");

    }

}