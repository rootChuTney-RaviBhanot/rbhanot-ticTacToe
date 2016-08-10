package com.tictactoe.application;

import com.google.gson.Gson;
import com.tictactoe.controller.GameRoute;
import com.tictactoe.model.utils.GameRouteInput;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.get;

/**
 * Created by ravi on 8/7/16.
 */
public class TicTacToeApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicTacToeApplication.class);
    private static Gson gson = new Gson();
    private static GameRoute gameRoute;

    public static void main(String[] args) {
        //Initialize dependencies
        gameRoute = new GameRoute();

        // Configure Spark
        String port = System.getenv("PORT");
        port((port!=null && StringUtils.isNotEmpty(port)) ? Integer.parseInt(port) : 8080);

        get("/health", (request, response) -> {
            response.status(200);
            return "I am alive!!";
        });

        post("/game", (request, response) -> {
            String requestBody = request.body();
            LOGGER.info("before request {}", requestBody);
            Map<String, String> params = parseRequestBody(request.body());

            GameRouteInput gameRouteInput = new GameRouteInput();
            gameRouteInput.setToken(params.get("token"));
            gameRouteInput.setTeam_id(params.get("team_id"));
            gameRouteInput.setTeam_domain(params.get("team_domain"));
            gameRouteInput.setChannel_id(params.get("channel_id"));
            gameRouteInput.setChannel_name(params.get("channel_name"));
            gameRouteInput.setUser_id(params.get("user_id"));
            gameRouteInput.setUser_name(params.get("user_name"));
            gameRouteInput.setCommand(params.get("command"));
            gameRouteInput.setText(params.get("text"));
            gameRouteInput.setResponse_url(params.get("response_url"));

            LOGGER.info("command : {}, text : {}, channel_id : {}, channelName : {}, user_id : {}, user_name : {}",
                    gameRouteInput.getCommand(), gameRouteInput.getText(), gameRouteInput.getChannel_id(), gameRouteInput.getChannel_name(),
                    gameRouteInput.getUser_id(), gameRouteInput.getUser_name());
            Object output = gameRoute.handleRequest(gameRouteInput);
            response.type("application/json");
            response.status(200);
            response.header("Access-Control-Allow-Origin", "*");
            return gson.toJson(output);
        });

    }

    public static Map<String, String> parseRequestBody(String body) {
        Map<String, String> params = new HashMap<String, String>();
        String[] keyValues = body.split("\\&");
        for (String s : keyValues) {
            String[] keyValue = s.split("=");
            String key = (keyValue.length > 0 && StringUtils.isNotEmpty(keyValue[0])) ? keyValue[0] : null;
            String value = (keyValue.length > 1) ? keyValue[1] : null;
            if (StringUtils.isNotEmpty(key)) {
                params.put(key, value);
            }
        }
        return params;
    }

}
