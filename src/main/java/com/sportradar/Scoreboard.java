package com.sportradar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

class Scoreboard {

    private final Set<Match> matches = new TreeSet<>(new MatchComparator());
    private final Set<String> teamsInvolved = new HashSet<>();

    List<Match> getMatches() {
        return new ArrayList<>(matches);
    }

    boolean addMatch(Match newMatch) {
        if (Objects.isNull(newMatch)) {
            throw new IllegalArgumentException("Match cannot be null");
        }
        return matches.add(newMatch);
    }

    boolean removeMatch(Match matchToRemove) {
        if (Objects.isNull(matchToRemove)) {
            throw new IllegalArgumentException("Match cannot be null");
        }
        return matches.remove(matchToRemove);
    }

    boolean addTeamsInvolved(String homeTeam, String awayTeam) {
        if (Objects.isNull(homeTeam) || Objects.isNull(awayTeam)) {
            throw new IllegalArgumentException("Team name cannot be null");
        }
        return teamsInvolved.add(homeTeam.trim().toLowerCase()) && teamsInvolved.add(awayTeam.trim().toLowerCase());
    }

    boolean removeTeamsInvolved(String homeTeam, String awayTeam) {
        if (Objects.isNull(homeTeam) || Objects.isNull(awayTeam)) {
            throw new IllegalArgumentException("Team name cannot be null");
        }
        return teamsInvolved.remove(homeTeam.trim().toLowerCase()) && teamsInvolved.remove(awayTeam.trim().toLowerCase());
    }

    boolean teamIsPlaying(String team) {
        if (Objects.isNull(team)) {
            throw new IllegalArgumentException("Team name cannot be null");
        }
        return teamsInvolved.contains(team.trim().toLowerCase());
    }

    Match findMatch(String homeTeam, String awayTeam) {
        if (Objects.isNull(homeTeam) || Objects.isNull(awayTeam)) {
            throw new IllegalArgumentException("Team name cannot be null");
        }
        return matches.stream()
                .filter(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam))
                .findFirst()
                .orElse(null);
    }

}
