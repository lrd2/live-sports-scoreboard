package com.sportradar;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MatchTest {

    private static final String SPAIN = "Spain";
    private static final String MEXICO = "Mexico";
    private static final int EXPECTED_HOME_SCORE = 0;
    private static final int EXPECTED_AWAY_SCORE = 0;
    private static final String SPAIN_UPPER_CASE = "SPAIN";

    @Test
    public void shouldCreateMatchWithGivenStartTime() {

        //given
        LocalDateTime startTime = LocalDateTime.now();

        //when
        Match match = new Match(SPAIN, MEXICO, startTime);

        //then
        assertAll(
                () -> assertEquals(SPAIN, match.getHomeTeam(), "Home team name is not as expected"),
                () -> assertEquals(MEXICO, match.getAwayTeam(), "Away team name is not as expected"),
                () -> assertEquals(startTime, match.getStartTime(), "Start time is not as expected"),
                () -> assertEquals(EXPECTED_HOME_SCORE, match.getHomeScore(), "Home score is not as expected"),
                () -> assertEquals(EXPECTED_AWAY_SCORE, match.getAwayScore(), "Away score is not as expected")
        );

    }

    @Test
    public void shouldCreateMatchWithDefaultStartTime() {

        //when
        Match match = new Match(SPAIN, MEXICO);

        //then
        assertAll(
                () -> assertEquals(SPAIN, match.getHomeTeam(), "Home team name is not as expected"),
                () -> assertEquals(MEXICO, match.getAwayTeam(), "Away team name is not as expected"),
                () -> assertNotNull(match.getStartTime(), "Start time is not as expected"),
                () -> assertEquals(EXPECTED_HOME_SCORE, match.getHomeScore(), "Home score is not as expected"),
                () -> assertEquals(EXPECTED_AWAY_SCORE, match.getAwayScore(), "Away score is not as expected")
        );

    }

    @Test
    public void shouldThrowWhenTeamNameIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Match(null, SPAIN));
        assertEquals("Team names cannot be null", exception.getMessage(), "Exception message is not as expected");
    }

    @Test
    public void shouldThrowWhenTeamNameIsEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Match(SPAIN, " "));
        assertEquals("Team names cannot be empty", exception.getMessage(), "Exception message is not as expected");
    }

    @Test
    public void shouldThrowWhenHomeAndAwayTeamNamesAreSame() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Match(SPAIN, SPAIN));
        assertEquals("Home and away team names must be different", exception.getMessage(), "Exception message is not as expected");
    }

    @Test
    public void shouldValidateIgnoreCaseAndThrow() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Match(SPAIN, SPAIN_UPPER_CASE));
        assertEquals("Home and away team names must be different", exception.getMessage(), "Exception message is not as expected");
    }

}