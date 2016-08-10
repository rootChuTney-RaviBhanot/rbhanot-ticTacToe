package com.tictactoe.routes;

import com.tictactoe.model.gamelogic.TicTacToeGame;
import com.tictactoe.model.utils.GameRouteInput;
import com.tictactoe.model.utils.GameRouteOutput;
import com.tictactoe.model.utils.TicTacToeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Class to handle play command
 */
public class PlayCommandHandler implements SlashCommandHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayCommandHandler.class);
    private static final String TTT_PLAY_SYNTAX = "[Usage] /ttt play position(number)";

    @Override
    public GameRouteOutput handle(GameRouteInput gameRouteInput, Map<String, TicTacToeGame> channelToGameMap) {

        // Get channel's game instance
        TicTacToeGame ticTacToeGame = channelToGameMap.get(gameRouteInput.getChannel_id());

        // Check if channel is valid
        if (gameRouteInput.getChannel_id() == null || !channelToGameMap.containsKey(gameRouteInput.getChannel_id())){
            return new GameRouteOutput(TicTacToeConstants.EPHEMERAL_RESPONSE_TYPE, "No game in progress at channel:" + gameRouteInput.getChannel_name());
        }

        // check if next move position is from the valid channel and player.
        if (!isUsersTurn(gameRouteInput, ticTacToeGame)){
            return new GameRouteOutput(TicTacToeConstants.EPHEMERAL_RESPONSE_TYPE, "Please wait for your turn.");
        }

        // Check if valid syntax of command
        String[] commandParts = gameRouteInput.getText().split(TicTacToeConstants.DELIMITER);
        int position;
        try {
            position = Integer.valueOf(commandParts[1]);
        } catch(NumberFormatException e){
            return new GameRouteOutput(TicTacToeConstants.EPHEMERAL_RESPONSE_TYPE, TTT_PLAY_SYNTAX);
        }

        try {
            ticTacToeGame.play(position);
            if (ticTacToeGame.getStatus() == TicTacToeConstants.Status.PLAYER1_WINS || ticTacToeGame.getStatus() == TicTacToeConstants.Status.PLAYER2_WINS){
                deleteFinishedGame(gameRouteInput, channelToGameMap);
                return new GameRouteOutput(TicTacToeConstants.IN_CHANNEL_RESPONSE_TYPE, ticTacToeGame.getCurrentMatrix() + "\n" + ticTacToeGame.getCurrentPlayer() + " wins.");
            } else if (ticTacToeGame.getStatus() == TicTacToeConstants.Status.DRAW){
                deleteFinishedGame(gameRouteInput, channelToGameMap);
                return new GameRouteOutput(TicTacToeConstants.IN_CHANNEL_RESPONSE_TYPE, ticTacToeGame.getCurrentMatrix()  + "\nDraw...");
            }
        } catch(IllegalArgumentException e){
            return new GameRouteOutput(TicTacToeConstants.EPHEMERAL_RESPONSE_TYPE, e.getMessage());
        }

        // A normal turn for a game still in progress
        String currentTurn = (ticTacToeGame.getStatus() == TicTacToeConstants.Status.IN_PROGRESS) ? ticTacToeGame.getCurrentTurn() : "";
        return new GameRouteOutput(TicTacToeConstants.IN_CHANNEL_RESPONSE_TYPE,
                gameRouteInput.getUser_name() + " marked at position:" + position + "\nCurrent board:\n" + ticTacToeGame.getCurrentMatrix() + "\n" + currentTurn);
    }

    /**
     * Check if current player's turn in the channel's game
     *
     * @param gameRouteInput input params
     * @param ticTacToeGame game instance for a channel
     * @return boolean
     */
    private boolean isUsersTurn(GameRouteInput gameRouteInput, TicTacToeGame ticTacToeGame) {
        if (!ticTacToeGame.getCurrentPlayer().equals(gameRouteInput.getUser_name())){
            return false;
        }
        return true;
    }

    public void deleteFinishedGame(GameRouteInput gameRouteInput, Map<String, TicTacToeGame> channelToGameMap) {
        channelToGameMap.remove(gameRouteInput.getChannel_id());
    }
}
