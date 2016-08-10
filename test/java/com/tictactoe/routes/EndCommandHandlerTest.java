package com.tictactoe.routes;

import com.tictactoe.model.gamelogic.TicTacToeGame;
import com.tictactoe.model.utils.GameRouteInput;
import com.tictactoe.model.utils.GameRouteOutput;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ravi on 8/10/16.
 */
public class EndCommandHandlerTest {
    private EndCommandHandler endCommandHandler = new EndCommandHandler();
    private GameRouteInput gameRouteInput = new GameRouteInput();
    private Map<String, TicTacToeGame> channelToGameMap = new HashMap<String, TicTacToeGame>();

    @Before
    public void initialize() {
        gameRouteInput.setCommand("/ttt");
        gameRouteInput.setText("end");
        gameRouteInput.setUser_name("abc");
        channelToGameMap.put("abcdef", new TicTacToeGame("abc", "def"));
    }

    @Test
    public void testPositiveHandle(){
        gameRouteInput.setChannel_id("abcdef");
        gameRouteInput.setChannel_name("first_channel");
        GameRouteOutput output = endCommandHandler.handle(gameRouteInput, channelToGameMap);
        assertNotNull(output);
    }

    @Test
    public void testNegativeHandle(){
        gameRouteInput.setChannel_id("xyzabc");
        gameRouteInput.setChannel_name("second_channel");
        GameRouteOutput output = endCommandHandler.handle(gameRouteInput, channelToGameMap);
        assertEquals(output.getText(), "No game running on channel:second_channel");
    }
}
