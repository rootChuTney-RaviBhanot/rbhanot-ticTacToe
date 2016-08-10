package com.tictactoe.model.gamelogic;

import com.tictactoe.model.utils.TicTacToeConstants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ravi on 8/10/16.
 */
public class TicTacToeGameTest {
    TicTacToeGame ticTacToeGame = null;

    @Test
    public void testPositiveDrawGame() {
        ticTacToeGame = new TicTacToeGame("abc", "def");
        ticTacToeGame.play(1);
        ticTacToeGame.play(7);
        ticTacToeGame.play(4);
        ticTacToeGame.play(2);
        ticTacToeGame.play(3);
        ticTacToeGame.play(5);
        ticTacToeGame.play(8);
        ticTacToeGame.play(6);
        ticTacToeGame.play(9);
        assertEquals(ticTacToeGame.getStatus(), TicTacToeConstants.Status.DRAW);
    }

    @Test
    public void testPositiveWinGame() {
        ticTacToeGame = new TicTacToeGame("abc", "def");
        ticTacToeGame.play(1);
        ticTacToeGame.play(2);
        ticTacToeGame.play(3);
        assertTrue(ticTacToeGame.getCurrentTurn().contains("def will play O"));
        assertEquals(ticTacToeGame.getCurrentPlayer(), "def");
        assertEquals(ticTacToeGame.getCurrentPlayerMark(), -1);
        ticTacToeGame.play(4);
        ticTacToeGame.play(5);
        ticTacToeGame.play(6);
        ticTacToeGame.play(7);
        assertEquals(ticTacToeGame.getStatus(), TicTacToeConstants.Status.PLAYER1_WINS);
        assertEquals(ticTacToeGame.getWinnerMark(), TicTacToeConstants.Mark_X);
    }

    @Test
    public void testPositiveEndGame() {
        ticTacToeGame = new TicTacToeGame("abc", "def");
        ticTacToeGame.play(1);
        ticTacToeGame.play(7);
        ticTacToeGame.play(4);
        ticTacToeGame.play(2);
        ticTacToeGame.play(3);
        ticTacToeGame.end();
        assertEquals(ticTacToeGame.getStatus(), TicTacToeConstants.Status.PLAYER2_ENDED);
    }

}
