package com.tictactoe.routes;

import com.tictactoe.application.TicTacToeApplication;
import com.tictactoe.model.gamelogic.TicTacToeGame;
import com.tictactoe.model.utils.GameRouteInput;
import com.tictactoe.model.utils.GameRouteOutput;
import com.tictactoe.model.utils.TicTacToeConstants;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Created by ravi on 8/7/16.
 */
public class StartCommandHandler implements SlashCommandHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(StartCommandHandler.class);

    @Override
    public GameRouteOutput handle(GameRouteInput gameRouteInput, Map<String, TicTacToeGame> channelToGameMap) {
        String[] commandParts = gameRouteInput.getText().split(TicTacToeConstants.DELIMITER);
        if (commandParts.length < 2) {
            return new GameRouteOutput(TicTacToeConstants.EPHEMERAL_RESPONSE_TYPE, "Need a competitor's name from this channel to start the game");
        } else if (commandParts.length > 2) {
            return new GameRouteOutput(TicTacToeConstants.EPHEMERAL_RESPONSE_TYPE, "Unnecessary extra parameters in command. Only two required.");
        }
        //Check if game already going on in the channel
        if (channelToGameMap.containsKey(gameRouteInput.getChannel_id())) {
            return new GameRouteOutput(TicTacToeConstants.EPHEMERAL_RESPONSE_TYPE, "Game already going on in the channel. Please try again, later.");
        }

        String competitorName = commandParts[1];
        String SLACK_BOT_TOKEN = System.getenv("SLACK_TOKEN");

        SlackSession session = SlackSessionFactory.createWebSocketSlackSession(SLACK_BOT_TOKEN);
        try {
            session.connect();
        } catch (IOException e) {
            return new GameRouteOutput(TicTacToeConstants.IN_CHANNEL_RESPONSE_TYPE, "Slack bot token value:" + SLACK_BOT_TOKEN + " unverified. Check token value and try again.");
        }

        //Check if channel exists
        SlackChannel slackChannel = session.findChannelById(gameRouteInput.getChannel_id());
        if (slackChannel == null){
            return new GameRouteOutput(TicTacToeConstants.IN_CHANNEL_RESPONSE_TYPE, "Unable to find channel with channel_id:" + gameRouteInput.getChannel_id());
        }

        //Check if competitor exists
        Collection<SlackUser> users = slackChannel.getMembers();
        if (users == null || users.isEmpty()){
            return new GameRouteOutput(TicTacToeConstants.IN_CHANNEL_RESPONSE_TYPE, "No user available in channel:"+gameRouteInput.getChannel_name());
        }

        Optional<SlackUser> competitorUser = users.stream().filter(slackUser -> slackUser.getUserName().equals(competitorName)).findFirst();
        if (!competitorUser.isPresent()){
            return new GameRouteOutput(TicTacToeConstants.IN_CHANNEL_RESPONSE_TYPE, "User:" + competitorName + " not found in channel:" + gameRouteInput.getChannel_name());
        }

        // Competitor exists. Good to start the game
        TicTacToeGame ticTacToeGame = new TicTacToeGame(gameRouteInput.getUser_name(), competitorName);
        channelToGameMap.putIfAbsent(gameRouteInput.getChannel_id(), ticTacToeGame);
        return new GameRouteOutput(TicTacToeConstants.IN_CHANNEL_RESPONSE_TYPE, "Game starting between " + ticTacToeGame.getPlayer1() + " and " + ticTacToeGame.getPlayer2());
    }
}
