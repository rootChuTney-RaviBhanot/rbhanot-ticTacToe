package com.tictactoe.routes;

import com.tictactoe.model.gamelogic.TicTacToeGame;
import com.tictactoe.model.utils.GameRouteInput;
import com.tictactoe.model.utils.GameRouteOutput;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by ravi on 8/10/16.
 */
public class PlayCommandHandlerTest {
    private PlayCommandHandler playCommandHandler = new PlayCommandHandler();
    private GameRouteInput gameRouteInput = new GameRouteInput();
    private Map<String, TicTacToeGame> channelToGameMap = new HashMap<String, TicTacToeGame>();

    @Before
    public void initialize() {
        gameRouteInput.setCommand("/ttt");
        gameRouteInput.setText("play+7");
        gameRouteInput.setUser_name("abc");
        gameRouteInput.setChannel_id("abcdef");
        gameRouteInput.setChannel_name("first_channel");
        channelToGameMap.put("abcdef", new TicTacToeGame("abc", "def"));
    }

    @Test
    public void testPositiveHandle(){
        GameRouteOutput output = playCommandHandler.handle(gameRouteInput, channelToGameMap);
        assertTrue(output.getText().contains("Current board:"));
    }

    @Test
    public void testNegativeUserHandle(){
        gameRouteInput.setUser_name("def");
        GameRouteOutput output = playCommandHandler.handle(gameRouteInput, channelToGameMap);
        assertEquals(output.getText(), "Please wait for your turn.");
    }

    @Test
    public void testNegativePlayHandle(){
        gameRouteInput.setUser_name("abc");
        gameRouteInput.setText("play+0");
        GameRouteOutput output = playCommandHandler.handle(gameRouteInput, channelToGameMap);
        assertEquals(output.getText(), "Invalid matrix position.");
        gameRouteInput.setText("play+0a");
        output = playCommandHandler.handle(gameRouteInput, channelToGameMap);
        assertEquals(output.getText(), "[Usage] /ttt play position(number)");
    }
}
