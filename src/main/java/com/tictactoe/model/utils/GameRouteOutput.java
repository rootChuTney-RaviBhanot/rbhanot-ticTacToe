package com.tictactoe.model.utils;

/**
 * Created by ravi on 8/8/16.
 */
public class GameRouteOutput {
    private String response_type;
    private String text;

    public GameRouteOutput() {}

    public GameRouteOutput(String response_type, String text) {
        this.response_type = response_type;
        this.text = text;
    }

    public String getResponse_type() {
        return response_type;
    }

    public void setResponse_type(String response_type) {
        this.response_type = response_type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
