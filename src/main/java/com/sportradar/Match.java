package com.sportradar;

import java.time.LocalDateTime;
import java.util.Objects;

public class Match {

    private final String homeTeam;
    private final String awayTeam;
    private final LocalDateTime startTime;

    private int homeScore = 0;
    private int awayScore = 0;

    Match(String homeTeam, String awayTeam, LocalDateTime startTime) {
        validateTeamNames(homeTeam, awayTeam);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startTime = Objects.isNull(startTime) ? LocalDateTime.now() : startTime;
    }

    Match(String homeTeam, String awayTeam) {
        this(homeTeam, awayTeam, null);
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    LocalDateTime getStartTime() {
        return startTime;
    }

    int getTotalScore() {
        return homeScore + awayScore;
    }

    void updateScores(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores must be non-negative.");
        }
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    private void validateTeamNames(String homeTeam, String awayTeam) {
        if (Objects.isNull(homeTeam) || Objects.isNull(awayTeam)) {
            throw new IllegalArgumentException("Team names cannot be null");
        }
        String trimmedHomeTeam = homeTeam.trim();
        String trimmedAwayTeam = awayTeam.trim();
        if (trimmedHomeTeam.isEmpty() || trimmedAwayTeam.isEmpty()) {
            throw new IllegalArgumentException("Team names cannot be empty");
        }
        if (trimmedHomeTeam.equalsIgnoreCase(trimmedAwayTeam)) {
            throw new IllegalArgumentException("Home and away team names must be different");
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return Objects.equals(homeTeam, match.homeTeam) && Objects.equals(awayTeam, match.awayTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam);
    }

}
