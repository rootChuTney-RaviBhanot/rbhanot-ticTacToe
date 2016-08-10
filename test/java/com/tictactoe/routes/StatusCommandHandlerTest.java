package com.tictactoe.routes;

import com.tictactoe.model.gamelogic.TicTacToeGame;
import com.tictactoe.model.utils.GameRouteInput;
import com.tictactoe.model.utils.GameRouteOutput;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * Created by ravi on 8/10/16.
 */
public class StatusCommandHandlerTest {
    private StatusCommandHandler statusCommandHandler = new StatusCommandHandler();
    private GameRouteInput gameRouteInput = new GameRouteInput();
    private Map<String, TicTacToeGame> channelToGameMap = new HashMap<String, TicTacToeGame>();

    @Before
    public void initialize() {
        gameRouteInput.setCommand("/ttt");
        gameRouteInput.setText("status");
    }

    @Test
    public void testPositiveHandle(){
        GameRouteOutput output = statusCommandHandler.handle(gameRouteInput, channelToGameMap);
        assertNotNull(output);
    }
}