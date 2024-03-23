package com.example.perfectmazeclient.requests.util;

import com.example.perfectmazeclient.requests.communication.Response;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ResponseUtils {

    private static Gson gson = new Gson();

    public static <T> Response buildResponse(String message, String status, String additionalDataKey , T additionalData) {

        JsonObject responseObject = initializeJsonResponseObject(message, status);

        if (additionalData != null) {
            responseObject.add(additionalDataKey, gson.toJsonTree(additionalData));
        }

        return new Response(responseObject);
    }

    public static Response buildResponse(String message, String status) {
        return buildResponse(message, status, null, null);
    }

    public static JsonObject initializeJsonResponseObject(String message, String status) {
        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("message", message);
        responseObject.addProperty("status", status);
        return responseObject;
    }
}