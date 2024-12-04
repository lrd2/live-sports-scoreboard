package com.sportradar;

import java.util.Objects;
import java.util.Set;

public class LiveSportsScoreboard {

    private final Scoreboard scoreboard;

    public LiveSportsScoreboard() {
        this.scoreboard = new Scoreboard();
    }

    public OperationResult startMatch(String homeTeam, String awayTeam) {
        if (Objects.isNull(homeTeam) || Objects.isNull(awayTeam)) {
            return MatchOperationResult.nullTeamNames();
        }
        Match newMatch = null;
        try {
            if (scoreboard.teamIsPlaying(homeTeam) || scoreboard.teamIsPlaying(awayTeam)) {
                return MatchOperationResult.alreadyPlaying();
            } else {
                newMatch = new Match(homeTeam, awayTeam);
                boolean matchAdded = scoreboard.addMatch(newMatch);
                boolean teamsArePlaying = scoreboard.addTeamsInvolved(homeTeam, awayTeam);
                if (matchAdded && teamsArePlaying) {
                    return MatchOperationResult.startedSuccessfully();
                } else {
                    rollbackStartMatch(homeTeam, awayTeam, newMatch);
                    return MatchOperationResult.notStarted();
                }
            }
        } catch (Exception e) {
            rollbackStartMatch(homeTeam, awayTeam, newMatch);
            return MatchOperationResult.unexpectedError(e.getMessage());
        }
    }

    public OperationResult updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        //TODO implement
        return null;
    }

    public Set<Match> getMatches() {
        return scoreboard.getMatches();
    }

    private void rollbackStartMatch(String homeTeam, String awayTeam, Match newMatch) {
        if (Objects.nonNull(newMatch) && scoreboard.removeMatch(newMatch)) {
            scoreboard.removeTeamsInvolved(homeTeam, awayTeam);
        }
    }

}
