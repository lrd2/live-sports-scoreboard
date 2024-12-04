package com.sportradar;

public class MatchOperationResult implements OperationResult {

    private static final String STARTED_SUCCESSFULLY = "Match started successfully.";
    private static final String TEAM_ALREADY_PLAYING = "One of the teams is already playing.";
    private static final String CANNOT_BE_NULL = "Team names cannot be null.";
    private static final String MATCH_COULD_NOT_BE_STARTED = "Match could not be started";
    private static final String MATCH_DOES_NOT_EXIST = "Match does not exist.";
    private static final String SCORES_UPDATED_SUCCESSFULLY = "Scores updated successfully.";
    private static final String FINISHED_SUCCESSFULLY = "Match finished successfully.";
    private static final String COULD_NOT_BE_FINISHED = "Match could not be finished";
    private static final String NAMES_CANNOT_BE_EMPTY = "Team names cannot be empty.";
    private static final String COULD_NOT_BE_UPDATED = "Match could not be updated.";

    private final boolean success;
    private final String message;

    private MatchOperationResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    static MatchOperationResult startedSuccessfully() {
        return new MatchOperationResult(true, STARTED_SUCCESSFULLY);
    }
    static MatchOperationResult notStarted() {
        return new MatchOperationResult(true, MATCH_COULD_NOT_BE_STARTED);
    }

    static MatchOperationResult alreadyPlaying() {
        return new MatchOperationResult(false, TEAM_ALREADY_PLAYING);
    }

    static MatchOperationResult unexpectedError(String message) {
        return new MatchOperationResult(false, message);
    }

    static MatchOperationResult nullTeamNames() {
        return new MatchOperationResult(false, CANNOT_BE_NULL);
    }

    static MatchOperationResult emptyTeamNames() {
        return new MatchOperationResult(false, NAMES_CANNOT_BE_EMPTY);
    }

    static MatchOperationResult finishedSuccessfully() {
        return new MatchOperationResult(true, FINISHED_SUCCESSFULLY);
    }

    static MatchOperationResult notExists() {
        return new MatchOperationResult(false, MATCH_DOES_NOT_EXIST);
    }

    static MatchOperationResult updatedSuccessfully() {
        return new MatchOperationResult(true, SCORES_UPDATED_SUCCESSFULLY);
    }

    static MatchOperationResult notUpdated() {
        return new MatchOperationResult(true, COULD_NOT_BE_UPDATED);
    }

    static MatchOperationResult notFinished() {
        return new MatchOperationResult(false, COULD_NOT_BE_FINISHED);
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
