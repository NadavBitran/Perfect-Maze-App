package com.example.perfectmazeclient.requests.handlers;

import com.example.perfectmazeclient.dm.Leaderboards;
import com.example.perfectmazeclient.exceptions.RequestFailed;
import com.example.perfectmazeclient.requests.communication.Response;
import com.example.perfectmazeclient.requests.communication.util.ResponseProperties;
import com.example.perfectmazeclient.requests.sender.Sender;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class LeaderboardRequests {
    private static final Gson gson = new Gson();
    private static final String GET_LEADERBOARDS = "Leaderboards/getLeaderboard";
    public static Leaderboards handleGetLeaderboardsRequest() throws RequestFailed {

        Response response = Sender.sendRequest(GET_LEADERBOARDS, null);

        JsonObject body = response.getBody().getAsJsonObject();

        String status = body.get(ResponseProperties.STATUS).getAsString();
        String message = body.get(ResponseProperties.MESSAGE).getAsString();

        if(!status.equals(ResponseProperties.STATUS_SUCCESS)) throw new RequestFailed(message);

        Leaderboards leaderboards = gson.fromJson(body.get(ResponseProperties.LEADERBOARDS), Leaderboards.class);

        return leaderboards;
    }
}
