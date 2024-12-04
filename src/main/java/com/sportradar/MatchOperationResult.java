package com.sportradar;

public class MatchOperationResult implements OperationResult {

    private static final String STARTED_SUCCESSFULLY = "Match started successfully.";
    private static final String TEAM_ALREADY_PLAYING = "One of the teams is already playing.";
    private static final String CANNOT_BE_NULL = "Team names cannot be null";
    private static final String MATCH_COULD_NOT_BE_STARTED = "Match could not be started";
    private static final String MUST_BE_NON_NEGATIVE = "Scores must be non-negative.";
    private static final String MATCH_DOES_NOT_EXIST = "Match does not exist.";
    private static final String SCORES_UPDATED_SUCCESSFULLY = "Scores updated successfully.";

    private final boolean success;
    private final String message;

    private MatchOperationResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static MatchOperationResult startedSuccessfully() {
        return new MatchOperationResult(true, STARTED_SUCCESSFULLY);
    }
    public static MatchOperationResult notStarted() {
        return new MatchOperationResult(true, MATCH_COULD_NOT_BE_STARTED);
    }

    public static MatchOperationResult alreadyPlaying() {
        return new MatchOperationResult(false, TEAM_ALREADY_PLAYING);
    }

    public static MatchOperationResult unexpectedError(String message) {
        return new MatchOperationResult(false, message);
    }

    public static MatchOperationResult nullTeamNames() {
        return new MatchOperationResult(false, CANNOT_BE_NULL);
    }

    public static MatchOperationResult invalidScore() {
        return new MatchOperationResult(false, MUST_BE_NON_NEGATIVE);
    }

    public static MatchOperationResult notExists() {
        return new MatchOperationResult(false, MATCH_DOES_NOT_EXIST);
    }

    public static MatchOperationResult updatedSuccessfully() {
        return new MatchOperationResult(true, SCORES_UPDATED_SUCCESSFULLY);
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
