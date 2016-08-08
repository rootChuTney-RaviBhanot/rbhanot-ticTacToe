package com.tictactoe.routes;

import com.tictactoe.model.gamelogic.TicTacToeGame;
import com.tictactoe.model.utils.GameRouteInput;
import com.tictactoe.model.utils.GameRouteOutput;
import com.tictactoe.model.utils.TicTacToeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by ravi on 8/7/16.
 */
public class EndCommandHandler implements SlashCommandHandler {
    private static final Logger LOGGER  = LoggerFactory.getLogger(EndCommandHandler.class);

    @Override
    public GameRouteOutput handle(GameRouteInput gameRouteInput, Map<String, TicTacToeGame> channelToGameMap) {
        // Check if channel is valid
        if (!channelToGameMap.containsKey(gameRouteInput.getChannel_id())) {
            return new GameRouteOutput(TicTacToeConstants.EPHEMERAL_RESPONSE_TYPE, "No game running on channel:" + gameRouteInput.getChannel_name());
        }

        // Check if terminating user has his/her turn currently
        TicTacToeGame ticTacToeGame = channelToGameMap.get(gameRouteInput.getChannel_id());
        if (!canResetTheGame(gameRouteInput, ticTacToeGame)){
            return new GameRouteOutput(TicTacToeConstants.EPHEMERAL_RESPONSE_TYPE, "Please wait for your turn to end game.");
        }

        ticTacToeGame.end();

        //Remove channel from map
        channelToGameMap.remove(gameRouteInput.getChannel_id());
        String winner = ticTacToeGame.getPlayer1().equals(ticTacToeGame.getCurrentPlayer())? ticTacToeGame.getPlayer2() : ticTacToeGame.getPlayer1();
        return new GameRouteOutput(TicTacToeConstants.IN_CHANNEL_RESPONSE_TYPE,
                gameRouteInput.getUser_name() + " ended the game. " + winner + " won!");
    }

    /**
     * <p>
     * Check if user is either player1 or player2 of current game so that
     * either one of them can draw the game even if it's not player's turn.
     * </p>
     *
     * @param gameRouteInput input params
     * @param ticTacToeGame game instance for a channel
     * @return boolean
     */
    private boolean canResetTheGame(GameRouteInput gameRouteInput, TicTacToeGame ticTacToeGame) {
        if (!ticTacToeGame.getCurrentPlayer().equals(gameRouteInput.getUser_name())){
            return false;
        }
        return true;
    }

}
