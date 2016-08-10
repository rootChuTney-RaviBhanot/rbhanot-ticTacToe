package com.tictactoe.routes;

import com.tictactoe.model.gamelogic.TicTacToeGame;
import com.tictactoe.model.utils.GameRouteInput;
import com.tictactoe.model.utils.GameRouteOutput;
import com.tictactoe.model.utils.TicTacToeConstants;

import java.util.Map;

/**
 * Class to handle help command response
 */
public class HelpCommandHandler implements SlashCommandHandler {
    private static final String HELP_TEXT = "/ttt start [user] : start a new TicTacToe game with user on a 3x3 board\n" +
            "/ttt play [position] : choose an empty position for your next play based on this given board\n" +
            " | 1 | 2 | 3 | \n | 4 | 5 | 6 | \n | 7 | 8 | 9 | \n" +
            "/ttt end : end the game at the current state leading to your opponent's victory. \n" +
            "/ttt status : display current board status with a reminder of whose turn it is.\n" +
            "/ttt help : manual for the ttt slash command.";

    @Override
    public GameRouteOutput handle(GameRouteInput gameRouteInput, Map<String, TicTacToeGame> channelToGameMap) {
        return new GameRouteOutput(TicTacToeConstants.EPHEMERAL_RESPONSE_TYPE, HELP_TEXT);
    }
}
