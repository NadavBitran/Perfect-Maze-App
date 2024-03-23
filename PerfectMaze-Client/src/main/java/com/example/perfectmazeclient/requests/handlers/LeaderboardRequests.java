package com.example.perfectmazeclient.requests.handlers;

import com.example.perfectmazeclient.dm.Leaderboards;
import com.example.perfectmazeclient.exceptions.RequestFailed;
import com.example.perfectmazeclient.requests.communication.Response;
import com.example.perfectmazeclient.requests.sender.Sender;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class LeaderboardRequests {
    private static final Gson gson = new Gson();
    private static final String GET_LEADERBOARDS = "Leaderboards/getLeaderboards";
    public static Leaderboards handleGetLeaderboardsRequest() throws RequestFailed {

        Response response = Sender.sendRequest(GET_LEADERBOARDS, null);

        JsonObject body = response.getBody().getAsJsonObject();

        String status = body.get("status").getAsString();
        String message = body.get("message").getAsString();

        if(!status.equals("success")) throw new RequestFailed(message);

        Leaderboards leaderboards = gson.fromJson(body.get("leaderboards"), Leaderboards.class);

        return leaderboards;
    }
}
