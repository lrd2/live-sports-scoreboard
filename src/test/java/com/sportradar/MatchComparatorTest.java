package com.sportradar;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class MatchComparatorTest {

    private static final String SPAIN = "Spain";
    private static final String MEXICO = "Mexico";
    private static final String SWEDEN = "Sweden";
    private static final String PORTUGAL = "Portugal";


    @Test
    public void shouldCompareMatchesByStartTimeWhenNoScore() {

        //given
        LocalDateTime now = LocalDateTime.now();
        Match match1 = new Match(SPAIN, MEXICO, now);
        Match match2 = new Match(SWEDEN, PORTUGAL, now.plusHours(1));

        //when
        int result = new MatchComparator().compare(match1, match2);

        //then
        assertTrue(result > 0, "Match with later start time should be considered smaller than match with earlier start time if scores are equal");

    }

    @Test
    public void shouldCompareMatchesByStartTimeWhenTotalScoresEqual() {

        //given
        LocalDateTime now = LocalDateTime.now();
        Match match1 = Mockito.mock(Match.class);
        Match match2 = Mockito.mock(Match.class);

        //when
        Mockito.when(match1.getTotalScore()).thenReturn(2);
        Mockito.when(match2.getTotalScore()).thenReturn(2);
        Mockito.when(match1.getStartTime()).thenReturn(now);
        Mockito.when(match2.getStartTime()).thenReturn(now.plusHours(1));
        int result = new MatchComparator().compare(match1, match2);

        //then
        assertTrue(result > 0, "Match with later start time should be considered smaller than match with earlier start time if scores are equal");

    }

    @Test
    public void shouldSortByTotalScoreWhenStartTimeIsSame() {

        //given
        LocalDateTime now = LocalDateTime.now();
        Match match1 = Mockito.mock(Match.class);
        Match match2 = Mockito.mock(Match.class);

        //when
        Mockito.when(match1.getTotalScore()).thenReturn(1);
        Mockito.when(match2.getTotalScore()).thenReturn(2);
        Mockito.when(match1.getStartTime()).thenReturn(now);
        Mockito.when(match2.getStartTime()).thenReturn(now);
        int result = new MatchComparator().compare(match1, match2);

        //then
        assertTrue(result > 0, "Match with higher score should be considered smaller than match with lower score if start times are the same");

    }

    @Test
    public void shouldCompareMatchesByTotalScore() {

        //given
        Match match1 = Mockito.mock(Match.class);
        Match match2 = Mockito.mock(Match.class);

        //when
        Mockito.when(match1.getTotalScore()).thenReturn(1);
        Mockito.when(match2.getTotalScore()).thenReturn(2);
        int result = new MatchComparator().compare(match1, match2);

        //then
        assertTrue(result > 0, "Match with higher score should be considered smaller than match with lower score if scores are different");

    }

    @Test
    public void shouldConsiderMatchesEqualWhenScoresAndStartTimeAreSame() {

        //given
        LocalDateTime now = LocalDateTime.now();
        Match match1 = Mockito.mock(Match.class);
        Match match2 = Mockito.mock(Match.class);

        //when
        Mockito.when(match1.getTotalScore()).thenReturn(1);
        Mockito.when(match2.getTotalScore()).thenReturn(1);
        Mockito.when(match1.getStartTime()).thenReturn(now);
        Mockito.when(match2.getStartTime()).thenReturn(now);
        int result = new MatchComparator().compare(match1, match2);

        //then
        assertEquals(0, result, "Matches should be considered equal when scores and start times are the same");

    }


}