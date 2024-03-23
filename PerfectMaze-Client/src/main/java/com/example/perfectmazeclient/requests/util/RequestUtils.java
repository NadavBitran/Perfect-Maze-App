package com.example.perfectmazeclient.requests.util;

import com.example.perfectmazeclient.requests.communication.Header;
import com.example.perfectmazeclient.requests.communication.Request;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class RequestUtils {
    private static Gson gson = new Gson();
    public static <T> Request buildRequest(String action, T body) {
        return new Request(initializeHeader(action), initializeBody(body));
    }

    private static Header initializeHeader(String action) {
        return new Header(action);
    }

    private static <T> JsonElement initializeBody(T body) {
        return gson.toJsonTree(body);
    }
}
