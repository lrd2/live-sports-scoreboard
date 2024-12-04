package com.sportradar;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoreboardTest {

    private static final String SPAIN = "Spain";
    private static final String MEXICO = "Mexico";

    @Test
    public void shouldAddMatch() {

        //given
        Scoreboard scoreboard = new Scoreboard();
        Match match = new Match(SPAIN, MEXICO);

        //when
        boolean result = scoreboard.addMatch(match);

        //then
        assertTrue(result, "Match should be added to the scoreboard");
    }

    @Test
    public void shouldAddTeamsInvolved() {

        //given
        Scoreboard scoreboard = new Scoreboard();

        //when
        boolean result = scoreboard.addTeamsInvolved(SPAIN, MEXICO);

        //then
        assertAll(
                () -> assertTrue(result, "Teams should be added to the scoreboard as involved"),
                () -> assertTrue(scoreboard.teamIsPlaying(SPAIN), "The team should be marked as playing"),
                () -> assertTrue(scoreboard.teamIsPlaying(MEXICO), "The team should be marked as playing")
        );

    }

    @Test
    public void shouldRemoveMatch() {

        //given
        Scoreboard scoreboard = new Scoreboard();
        Match match = new Match(SPAIN, MEXICO);
        scoreboard.addMatch(match);

        //when
        boolean result = scoreboard.removeMatch(match);

        //then
        assertTrue(result, "Match should be removed from the scoreboard");
    }

    @Test
    public void shouldRemoveTeamsInvolved() {

        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.addTeamsInvolved(SPAIN, MEXICO);

        //when
        boolean result = scoreboard.removeTeamsInvolved(SPAIN, MEXICO);

        //then
        assertAll(
                () -> assertTrue(result, "Teams should be removed from the scoreboard as involved"),
                () -> assertFalse(scoreboard.teamIsPlaying(SPAIN), "The team should be marked as not playing"),
                () -> assertFalse(scoreboard.teamIsPlaying(MEXICO), "The team should be marked as not playing")
        );

    }

    @Test
    public void shouldThrowExceptionWhenAddedMatchIsNull() {

        //given
        Scoreboard scoreboard = new Scoreboard();

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> scoreboard.addMatch(null));

        //then
        assertTrue(exception.getMessage().contains("Match cannot be null"), "Exception should be thrown when match is null");

    }

    @Test
    public void shouldThrowExceptionWhenAddedTeamIsNull() {

        //given
        Scoreboard scoreboard = new Scoreboard();

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> scoreboard.addTeamsInvolved(SPAIN, null));

        //then
        assertTrue(exception.getMessage().contains("Team name cannot be null"), "Exception should be thrown when team is null");

    }

    @Test
    public void shouldThrowExceptionWhenRemovedMatchIsNull() {

        //given
        Scoreboard scoreboard = new Scoreboard();

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> scoreboard.removeMatch(null));

        //then
        assertTrue(exception.getMessage().contains("Match cannot be null"), "Exception should be thrown when match is null");

    }

    @Test
    public void shouldThrowExceptionWhenRemovedTeamIsNull() {

        //given
        Scoreboard scoreboard = new Scoreboard();

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> scoreboard.removeTeamsInvolved(SPAIN, null));

        //then
        assertTrue(exception.getMessage().contains("Team name cannot be null"), "Exception should be thrown when team is null");

    }

    @Test
    public void shouldNotRemoveNonExistentMatch() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        Match match = new Match(SPAIN, MEXICO);

        //when
        boolean result = scoreboard.removeMatch(match);

        //then
        assertFalse(result, "Removing a non-existent match should return false");
    }

    @Test
    public void shouldNotRemoveNonExistentTeam() {
        //given
        Scoreboard scoreboard = new Scoreboard();

        //when
        boolean result = scoreboard.removeTeamsInvolved(SPAIN, MEXICO);

        //then
        assertFalse(result, "Removing non-existent teams should return false");
    }

    @Test
    public void shouldMaintainStateConsistencyWhenAddingMatch() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        Match match = new Match(SPAIN, MEXICO);

        //when
        scoreboard.addMatch(match);
        scoreboard.addTeamsInvolved(SPAIN, MEXICO);

        //then
        assertAll(
                () -> assertTrue(scoreboard.teamIsPlaying(SPAIN), "Spain should be marked as playing"),
                () -> assertTrue(scoreboard.teamIsPlaying(MEXICO), "Mexico should be marked as playing")
        );
    }

    @Test
    public void shouldGetMatches() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        Match match = new Match(SPAIN, MEXICO);
        scoreboard.addMatch(match);

        //when
        List<Match> matches = scoreboard.getMatches();

        //then
        assertTrue(matches.contains(match), "The match should be in the list of matches");
    }

    @Test
    public void shouldFindMatch() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        Match match = new Match(SPAIN, MEXICO);
        scoreboard.addMatch(match);

        //when
        Match foundMatch = scoreboard.findMatch(SPAIN, MEXICO);

        //then
        assertTrue(match.equals(foundMatch), "The match should be found");
    }

    @Test
    public void shouldNotFindMatch() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        Match match = new Match(SPAIN, MEXICO);
        scoreboard.addMatch(match);

        //when
        Match foundMatch = scoreboard.findMatch(MEXICO, SPAIN);

        //then
        assertTrue(foundMatch == null, "The match should not be found");

    }

    @Test
    public void shouldThrowWhenTeamNameIsNull() {

        //given
        Scoreboard scoreboard = new Scoreboard();

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> scoreboard.findMatch(SPAIN, null));

        //then
        assertTrue(exception.getMessage().contains("Team name cannot be null"), "Exception should be thrown when team is null");
    }

}