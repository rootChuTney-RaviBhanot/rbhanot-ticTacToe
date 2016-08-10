package com.tictactoe.routes;

import com.tictactoe.model.gamelogic.TicTacToeGame;
import com.tictactoe.model.utils.GameRouteInput;
import com.tictactoe.model.utils.GameRouteOutput;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ravi on 8/10/16.
 */
public class StartCommandHandlerTest {
    private StartCommandHandler startCommandHandler = new StartCommandHandler();
    private GameRouteInput gameRouteInput = new GameRouteInput();
    private Map<String, TicTacToeGame> channelToGameMap = new HashMap<String, TicTacToeGame>();

    @Before
    public void initialize() {
        gameRouteInput.setCommand("/ttt");
        gameRouteInput.setText("start+def");
        gameRouteInput.setUser_name("abc");
        gameRouteInput.setChannel_id("abcdef");
        gameRouteInput.setChannel_name("first_channel");
        channelToGameMap.put("abcdef", new TicTacToeGame("xyz", "abc"));
    }

    @Test
    public void testNegativeChannelHandle(){
        GameRouteOutput output = startCommandHandler.handle(gameRouteInput, channelToGameMap);
        assertEquals(output.getText(), "Game already going on in the channel. Please try again, later.");
    }

    @Test
    public void testNegativeTextHandle(){
        gameRouteInput.setUser_name("abc");
        gameRouteInput.setText("start+0+a");
        GameRouteOutput output = startCommandHandler.handle(gameRouteInput, channelToGameMap);
        assertEquals(output.getText(), "Unnecessary extra parameters in command. Only two required.");
    }
}
