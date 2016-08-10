package com.tictactoe.controller;

import com.tictactoe.model.gamelogic.TicTacToeGame;
import com.tictactoe.model.utils.GameRouteInput;
import com.tictactoe.model.utils.GameRouteOutput;
import com.tictactoe.routes.PlayCommandHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for GameRoute
 */
public class GameRouteTest {
    private GameRouteInput gameRouteInput = new GameRouteInput();

    @Before
    public void initialize() {
        gameRouteInput.setCommand("/ttt");
        gameRouteInput.setText("help");
        gameRouteInput.setUser_name("abc");
        gameRouteInput.setChannel_id("abcdef");
        gameRouteInput.setChannel_name("first_channel");
    }

    @Test
    public void testPositiveHandleRequest(){
        GameRouteOutput output = GameRoute.handleRequest(gameRouteInput);
        assertTrue(output.getText().contains("manual for the ttt slash command"));
    }

    @Test
    public void testNegativeHandleRequest(){
        gameRouteInput.setText("xyz");
        GameRouteOutput output = GameRoute.handleRequest(gameRouteInput);
        assertEquals(output.getText(), "Invalid input command");
    }
}
