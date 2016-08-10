package com.tictactoe.model.gamelogic;

import com.tictactoe.model.utils.TicTacToeConstants;

/**
 * Class containing logic of the whole game
 * matrix - the board of the game
 * player1 - first player in the game
 * player2 - second player in the game
 * currentPlayer - currently turn of which player
 * currentPlayerMark - current turn player's marking in the game
 * numPlays - number of turns already played
 * status - current game status
 */
public class TicTacToeGame {
    private final int[][] matrix;
    private String player1; 
    private String player2;
    private String currentPlayer;
    private int currentPlayerMark;
    private int numPlays;
    private TicTacToeConstants.Status status = TicTacToeConstants.Status.IN_PROGRESS;

    public TicTacToeGame(String player1, final String player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.currentPlayerMark = TicTacToeConstants.Mark_X;
        matrix = new int[TicTacToeConstants.BOARD_SIZE][TicTacToeConstants.BOARD_SIZE];
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public int getCurrentPlayerMark() {
        return currentPlayerMark;
    }

    public int getNumPlays() {
        return numPlays;
    }

    public TicTacToeConstants.Status getStatus() {
        return status;
    }

    public void setStatus(TicTacToeConstants.Status status) {
        this.status = status;
    }

    /**
     * A single player's turn on matrix with given position based on -
     * 1 | 2 | 3
     * 4 | 5 | 6
     * 7 | 8 | 9
     *
     * @param position position for playing; valid values from 1 to 9.
     * @return true if this player playing the turn wins the game after the chance, else false.
     * @throws IllegalArgumentException
     */
    public void play(int position) throws IllegalArgumentException {
        if (position < 1 || position > 9) {
            throw new IllegalArgumentException("Invalid matrix position.");
        }

        int row = (position - 1)/ TicTacToeConstants.BOARD_SIZE;
        int column = (position - 1) % TicTacToeConstants.BOARD_SIZE;

        if (matrix[row][column] != TicTacToeConstants.EMPTY) {
            throw new IllegalArgumentException("Invalid Move. Position already played.");
        }

        matrix[row][column] = currentPlayerMark;
        numPlays++;

        // check if current player has won
        if (hasCompletedLine(currentPlayerMark)){
            status = currentPlayer.equals(player1)? TicTacToeConstants.Status.PLAYER1_WINS : TicTacToeConstants.Status.PLAYER2_WINS;
            return;
        }

        // check if draw
        if (numPlays == TicTacToeConstants.BOARD_SIZE * TicTacToeConstants.BOARD_SIZE) {
            status = TicTacToeConstants.Status.DRAW;
            return;
        }

        // change current player
        currentPlayer = currentPlayer.equals(player1)? player2 : player1;
        currentPlayerMark = - currentPlayerMark;
    }

    /**
     * Check if there is a completed line with given mark.
     * @param mark
     * @return boolean
     */
    public boolean hasCompletedLine(int mark) {
        return hasCompletedRow(mark) || hasCompletedColumn(mark) || hasCompletedDiagonal(mark) || hasCompletedReverseDiagonal(mark);
    }

    /**
     * Check if a row has the same mark
     * @param mark
     * @return boolean
     */
    private boolean hasCompletedRow(int mark) {
        int condition = mark * TicTacToeConstants.BOARD_SIZE;
        int count;
        for (int i = 0 ; i < TicTacToeConstants.BOARD_SIZE; ++i) {
            count = 0;
            for (int j = 0; j < TicTacToeConstants.BOARD_SIZE; ++j) {
                count += matrix[i][j];
            }
            if (count == condition){
                return true;
            }
        }
        return false;
    }

    /**
     ** Check if a column has the same mark
     * @param mark
     * @return boolean
     */
    private boolean hasCompletedColumn(int mark) {
        int condition = mark * TicTacToeConstants.BOARD_SIZE;
        int count;
        for (int i = 0 ; i < TicTacToeConstants.BOARD_SIZE; ++i) {
            count = 0;
            for (int j = 0; j < TicTacToeConstants.BOARD_SIZE; ++j){
                count += matrix[j][i];
            }
            if (count == condition){
                return true;
            }
        }
        return false;
    }

    /**
     * Check if diagonal has the same mark
     * @param mark
     * @return boolean
     */
    private boolean hasCompletedDiagonal(int mark) {
        int condition = mark * TicTacToeConstants.BOARD_SIZE;
        int count = 0;
        for (int i = 0, j = 0 ; i < TicTacToeConstants.BOARD_SIZE && j < TicTacToeConstants.BOARD_SIZE; ++i, ++j) {
            count += matrix[j][i];
        }
        return (count == condition);
    }

    /**
     * Check if reverse diagonal has the same mark
     * @param mark
     * @return boolean
     */
    private boolean hasCompletedReverseDiagonal(int mark) {
        int condition = mark * TicTacToeConstants.BOARD_SIZE;
        int count = 0;
        for (int i = 2, j = 0 ; i >= 0; --i, ++j) {
            count += matrix[j][i];
        }
        return (count == condition);
    }

    /**
     *  Stringify current matrix
     */
    public String getCurrentMatrix() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < TicTacToeConstants.BOARD_SIZE; ++row) {
            for (int col = 0; col < TicTacToeConstants.BOARD_SIZE; ++col) {
                switch (matrix[row][col]) {
                    case TicTacToeConstants.Mark_X:
                        sb.append(" X ");
                        break;
                    case TicTacToeConstants.Mark_O:
                        sb.append(" O ");
                        break;
                    case TicTacToeConstants.EMPTY:
                        sb.append(" . ");
                        break;
                    default:
                        break;
                }
                if (col < TicTacToeConstants.BOARD_SIZE) {
                    sb.append("|");
                }
            }
            if (row < TicTacToeConstants.BOARD_SIZE){
                sb.append("\n-----------\n");
            }
        }
        return sb.toString();
    }

    /**
     * Get currentPlayer and currentPlayerMark
     * @return String
     */
    public String getCurrentTurn(){
        if (this.currentPlayerMark == TicTacToeConstants.Mark_X){
            return "Current player:" + player1 + " will play X";
        }
        else if(this.currentPlayerMark == TicTacToeConstants.Mark_O){
            return "Current player " + player2 + " will play O";
        }
        return "";
    }

    /**
     * Method to update status if either player ended the game
     */
    public void end() {
        status = player1.equals(currentPlayer)? TicTacToeConstants.Status.PLAYER1_ENDED : TicTacToeConstants.Status.PLAYER2_ENDED;
    }

    /**
     * Get winner's mark
     * @return 1 for X, -1 for O; If game is draw, return 0.
     */
    public int getWinnerMark() {
        if (hasCompletedLine(TicTacToeConstants.Mark_X)) {
            return TicTacToeConstants.Mark_X;
        }
        else if (hasCompletedLine(TicTacToeConstants.Mark_O)) {
            return TicTacToeConstants.Mark_O;
        }
        else {
            return 0;
        }
    }
}
