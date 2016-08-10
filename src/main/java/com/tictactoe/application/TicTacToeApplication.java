package com.tictactoe.application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tictactoe.controller.GameRoute;
import com.tictactoe.model.utils.GameRouteInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.get;

/**
 * Created by ravi on 8/7/16.
 */
public class TicTacToeApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicTacToeApplication.class);
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static String SLACK_TOKEN = null;


    public static void main(String[] args) {
        // Read properties
        SLACK_TOKEN = getProperties().getProperty("slack_token");

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

    public static Properties getProperties() {
        Properties properties = new Properties();
        InputStream input = null;
        try {

            String filename = "app.properties";
            input = TicTacToeApplication.class.getClassLoader().getResourceAsStream(filename);
            if(input==null){
                LOGGER.error("Sorry, unable to find " + filename);
                return null;
            }

            //load a properties file from class path, inside static method
            properties.load(input);
        } catch (IOException ex) {
            LOGGER.error("Unable to load properties file {}",ex.getLocalizedMessage());
        } finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    LOGGER.error("Unable to load properties file {}",e.getLocalizedMessage());
                }
            }
        }
        return properties;
    }

}
