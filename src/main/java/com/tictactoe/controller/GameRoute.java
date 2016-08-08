package com.tictactoe.controller;

import com.tictactoe.model.gamelogic.TicTacToeGame;
import com.tictactoe.model.utils.GameRouteInput;
import com.tictactoe.model.utils.GameRouteOutput;
import com.tictactoe.model.utils.TicTacToeConstants;
import com.tictactoe.routes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ravi on 8/8/16.
 */
public class GameRoute {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameRoute.class);
    private static Map<String, TicTacToeGame> channelToGameMap = new ConcurrentHashMap<>();
    private static StartCommandHandler startCommandHandler = new StartCommandHandler();
    private static PlayCommandHandler playCommandHandler = new PlayCommandHandler();
    private static EndCommandHandler endCommandHandler = new EndCommandHandler();
    private static StatusCommandHandler statusCommandHandler = new StatusCommandHandler();
    private static HelpCommandHandler helpCommandHandler = new HelpCommandHandler();

    /**
     *
     * slackCommand.getText() will be the command for the game
     *
     * start : start a new game with a user
     * play :  position chose to play by current player
     * end : end the game in the current state
     * status : display current board status with a reminder of whose turn it is
     * help : prints game rules and sample commands
     *
     * @param
     * @return GameRouteOutput
     */
    public static GameRouteOutput handleRequest(GameRouteInput gameRouteInput) {
        LOGGER.info("command : {}, text : {}, channel_id : {}, channelName : {}, user_id : {}, user_name : {}",
                gameRouteInput.getCommand(), gameRouteInput.getText(), gameRouteInput.getChannel_id(), gameRouteInput.getChannel_name(),
                gameRouteInput.getUser_id(), gameRouteInput.getUser_name());
        GameRouteOutput output;
        String[] commandParts = gameRouteInput.getCommand().split(TicTacToeConstants.DELIMITER);
        String option = commandParts[0];
        switch(option.toLowerCase()){
            case "start":
                output = startCommandHandler.handle(gameRouteInput, channelToGameMap);
                break;
            case "play":
                output =  playCommandHandler.handle(gameRouteInput, channelToGameMap);
                break;
            case "end":
                output = endCommandHandler.handle(gameRouteInput, channelToGameMap);
                break;
            case "status":
                output = statusCommandHandler.handle(gameRouteInput, channelToGameMap);
                break;
            case "help":
                output = helpCommandHandler.handle(gameRouteInput, channelToGameMap);
                break;
            default:
                output = new GameRouteOutput();
                output.setResponse_type(TicTacToeConstants.EPHEMERAL_RESPONSE_TYPE);
                output.setText("Invalid input command");
        }

        return output;
    }
}
