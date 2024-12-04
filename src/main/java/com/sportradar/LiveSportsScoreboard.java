package com.sportradar;

import java.util.List;
import java.util.Objects;

public class LiveSportsScoreboard {

    private final Scoreboard scoreboard;

    public LiveSportsScoreboard() {
        this.scoreboard = new Scoreboard();
    }

    public OperationResult startMatch(String homeTeam, String awayTeam) {
        OperationResult validationResult = validateTeamNames(homeTeam, awayTeam);
        if (Objects.nonNull(validationResult)) return validationResult;

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
        OperationResult validationResult = validateTeamNames(homeTeam, awayTeam);
        if (Objects.nonNull(validationResult)) return validationResult;

        Integer homeScoreBeforeUpdate = null;
        Integer awayScoreBeforeUpdate = null;
        Match matchToUpdate = null;

        try {
            matchToUpdate = scoreboard.findMatch(homeTeam, awayTeam);
            if (Objects.isNull(matchToUpdate)) {
                return MatchOperationResult.notExists();
            }
            homeScoreBeforeUpdate = matchToUpdate.getHomeScore();
            awayScoreBeforeUpdate = matchToUpdate.getAwayScore();
            matchToUpdate.updateScores(homeScore, awayScore);
            boolean scoreboardUpdated = scoreboard.updateScoreboardAfterMatchUpdate(matchToUpdate);
            if (scoreboardUpdated) {
                return MatchOperationResult.updatedSuccessfully();
            }
            rollbackUpdateScore(homeScoreBeforeUpdate, awayScoreBeforeUpdate, matchToUpdate);
            return MatchOperationResult.updatedSuccessfully();
        } catch (Exception e) {
            rollbackUpdateScore(homeScoreBeforeUpdate, awayScoreBeforeUpdate, matchToUpdate);
            return MatchOperationResult.unexpectedError(e.getMessage());
        }
    }

    public OperationResult finishMatch(String homeTeam, String awayTeam) {
        OperationResult validationResult = validateTeamNames(homeTeam, awayTeam);
        if (Objects.nonNull(validationResult)) return validationResult;

        boolean matchFinished = false;
        boolean teamsInvolvedRemoved = false;
        Match matchToFinish = null;

        try {
            matchToFinish = scoreboard.findMatch(homeTeam, awayTeam);
            if (Objects.isNull(matchToFinish)) {
                return MatchOperationResult.notExists();
            }
            matchFinished = scoreboard.removeMatch(matchToFinish);
            teamsInvolvedRemoved = scoreboard.removeTeamsInvolved(homeTeam, awayTeam);
            if (matchFinished && teamsInvolvedRemoved) {
                return MatchOperationResult.finishedSuccessfully();
            }
            rollbackFinishMatch(homeTeam, awayTeam, matchFinished, matchToFinish, teamsInvolvedRemoved);
            return MatchOperationResult.notFinished();
        } catch (Exception e) {
            rollbackFinishMatch(homeTeam, awayTeam, matchFinished, matchToFinish, teamsInvolvedRemoved);
            return MatchOperationResult.unexpectedError(e.getMessage());
        }
    }

    public List<Match> getMatches() {
        return scoreboard.getMatches();
    }

    private void rollbackFinishMatch(String homeTeam, String awayTeam, boolean matchFinished, Match matchToFinish, boolean teamsInvolvedRemoved) {
        if (matchFinished) {
            scoreboard.addMatch(matchToFinish);
        }
        if (teamsInvolvedRemoved) {
            scoreboard.addTeamsInvolved(homeTeam, awayTeam);
        }
    }

    private void rollbackStartMatch(String homeTeam, String awayTeam, Match newMatch) {
        if (Objects.nonNull(newMatch) && scoreboard.removeMatch(newMatch)) {
            scoreboard.removeTeamsInvolved(homeTeam, awayTeam);
        }
    }

    private void rollbackUpdateScore(Integer homeScoreBeforeUpdate, Integer awayScoreBeforeUpdate, Match matchToUpdate) {
        if (Objects.nonNull(matchToUpdate) && Objects.nonNull(homeScoreBeforeUpdate) && Objects.nonNull(awayScoreBeforeUpdate)) {
            matchToUpdate.updateScores(homeScoreBeforeUpdate, awayScoreBeforeUpdate);
            scoreboard.updateScoreboardAfterMatchUpdate(matchToUpdate);
        }
    }

    private OperationResult validateTeamNames(String homeTeam, String awayTeam) {
        if (Objects.isNull(homeTeam) || Objects.isNull(awayTeam)) {
            return MatchOperationResult.nullTeamNames();
        }
        if (homeTeam.trim().isEmpty() || awayTeam.trim().isEmpty()) {
            return MatchOperationResult.emptyTeamNames();
        }
        return null;
    }

}
