package com.tictactoe.application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tictactoe.controller.GameRoute;
import com.tictactoe.model.utils.GameRouteInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.get;

/**
 * Created by ravi on 8/7/16.
 */
public class TicTacToeApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicTacToeApplication.class);
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        // Configure Spark
        port(8080);

        get("/health", (request, response) -> {
            response.status(200);
            return "I am alive!!";
        });

        post("/game", (request, response) -> {
            String requestBody = request.body();
            Object output = GameRoute.handleRequest(gson.fromJson(request.body(), GameRouteInput.class));
            response.type("application/json");
            response.status(200);
            response.header("Access-Control-Allow-Origin", "*");
            return gson.toJson(output);
        });

    }

}
