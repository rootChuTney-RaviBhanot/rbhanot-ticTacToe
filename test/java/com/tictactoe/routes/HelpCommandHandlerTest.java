package com.tictactoe.routes;

import com.tictactoe.application.TicTacToeApplication;
import com.tictactoe.model.gamelogic.TicTacToeGame;
import com.tictactoe.model.utils.GameRouteInput;
import com.tictactoe.model.utils.GameRouteOutput;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by ravi on 8/10/16.
 */
public class HelpCommandHandlerTest {
    private HelpCommandHandler helpCommandHandler = new HelpCommandHandler();
    private GameRouteInput gameRouteInput = new GameRouteInput();
    private Map<String, TicTacToeGame> channelToGameMap = new HashMap<String, TicTacToeGame>();

    @Before
    public void initialize() {
        gameRouteInput.setCommand("/ttt");
        gameRouteInput.setText("help");
    }

    @Test
    public void testPositiveHandle(){
        GameRouteOutput output = helpCommandHandler.handle(gameRouteInput, channelToGameMap);
        assertNotNull(output);
    }
}
