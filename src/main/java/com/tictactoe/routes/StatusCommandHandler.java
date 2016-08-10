package com.tictactoe.routes;

import com.tictactoe.model.gamelogic.TicTacToeGame;
import com.tictactoe.model.utils.GameRouteInput;
import com.tictactoe.model.utils.GameRouteOutput;
import com.tictactoe.model.utils.TicTacToeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Class to handle status command
 */
public class StatusCommandHandler implements SlashCommandHandler {
    private static final Logger LOGGER  = LoggerFactory.getLogger(StatusCommandHandler.class);


    @Override
    public GameRouteOutput handle(GameRouteInput gameRouteInput, Map<String, TicTacToeGame> channelToGameMap) {
        // Check if channel is valid
        if (!channelToGameMap.containsKey(gameRouteInput.getChannel_id())) {
            return new GameRouteOutput(TicTacToeConstants.EPHEMERAL_RESPONSE_TYPE, "No game on channel:" + gameRouteInput.getChannel_name());
        }

        TicTacToeGame ticTacToeGame = channelToGameMap.get(gameRouteInput.getChannel_id());
        return new GameRouteOutput(TicTacToeConstants.IN_CHANNEL_RESPONSE_TYPE, ticTacToeGame.getCurrentMatrix() + "\n" + ticTacToeGame.getCurrentTurn());
    }
    
}
