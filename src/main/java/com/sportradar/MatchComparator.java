package com.sportradar;

import java.util.Comparator;

class MatchComparator implements Comparator<Match> {

    @Override
    public int compare(Match m1, Match m2) {
        int scoreComparison = Integer.compare(
                m2.getTotalScore(),
                m1.getTotalScore()
        );
        if (scoreComparison != 0) {
            return scoreComparison;
        }
        return m2.getStartTime().compareTo(m1.getStartTime());
    }

}