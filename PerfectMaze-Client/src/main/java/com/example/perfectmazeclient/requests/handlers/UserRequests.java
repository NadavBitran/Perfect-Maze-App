package com.example.perfectmazeclient.requests.handlers;

import com.example.perfectmazeclient.dm.User;
import com.example.perfectmazeclient.exceptions.RequestFailed;
import com.example.perfectmazeclient.requests.communication.Response;
import com.example.perfectmazeclient.requests.sender.Sender;
import com.example.perfectmazeclient.util.LoggedUser;
import com.google.gson.JsonObject;

public class UserRequests {
    private static final String LOGIN = "User/login";
    private static final String REGISTER = "User/register";
    public static void handleLoginRequest(String email, String password) throws RequestFailed {

        User user = new User(email, password);

        Response response = Sender.sendRequest(LOGIN, user);

        JsonObject body = response.getBody().getAsJsonObject();

        String status = body.get("status").getAsString();
        String message = body.get("message").getAsString();
        String userId = body.get("userId").getAsString();

        if(!status.equals("success")) throw new RequestFailed(message);

        user.setUserId(userId);

        LoggedUser.setLoggedUser(user);
    }
    public static void handleRegisterRequest(String email, String password, String username) throws RequestFailed {

        User user = new User(email, password, username);

        Response response = Sender.sendRequest(REGISTER, user);

        JsonObject body = response.getBody().getAsJsonObject();

        String status = body.get("status").getAsString();
        String message = body.get("message").getAsString();
        String userId = body.get("userId").getAsString();

        if(!status.equals("success")) throw new RequestFailed(message);

        user.setUserId(userId);

        LoggedUser.setLoggedUser(user);
    }

}
