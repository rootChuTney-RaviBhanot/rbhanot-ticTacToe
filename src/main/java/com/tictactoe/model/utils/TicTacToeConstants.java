package com.tictactoe.model.utils;

/**
 * Constants used by the application
 */
public class TicTacToeConstants {
    public static final String DELIMITER = "\\+";
    public static final String EPHEMERAL_RESPONSE_TYPE = "ephemeral";
    public static final String IN_CHANNEL_RESPONSE_TYPE = "in_channel";
    public enum Status {
        IN_PROGRESS, PLAYER1_WINS, PLAYER2_WINS, DRAW, PLAYER1_ENDED, PLAYER2_ENDED;
    }
    public static final int BOARD_SIZE = 3;
    public static final int Mark_X = 1;
    public static final int Mark_O = -1;
    public static final int EMPTY = 0;
}