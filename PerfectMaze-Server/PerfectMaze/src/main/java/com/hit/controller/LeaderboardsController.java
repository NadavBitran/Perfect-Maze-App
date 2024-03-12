package com.hit.controller;

import com.google.gson.JsonElement;
import com.hit.dm.Leaderboards;
import com.hit.exceptions.ControllerRoutingFailed;
import com.hit.exceptions.ServiceRequestFailedException;
import com.hit.server.Request;
import com.hit.server.Response;
import com.hit.server.util.ResponseUtils;
import com.hit.service.LeaderboardService;

public class LeaderboardsController implements IController {

    private LeaderboardService leaderboardService;

    public LeaderboardsController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @Override
    public Response executeAction(Request request) throws ControllerRoutingFailed {

        String methodAction = request.getHeaders().getAction().split("/")[1];
        String currentController = request.getHeaders().getAction().split("/")[0];

        switch(methodAction) {
            case "getLeaderboard":
                return getLeaderboard();
            default:
                throw new ControllerRoutingFailed("Failed to route to method action: " + methodAction + " in controller: " + currentController);
        }
    }

    public Response getLeaderboard() {
        Response response = null;

        try
        {
            Leaderboards leaderboards = leaderboardService.getLeaderboards();
            response = ResponseUtils.buildResponse("Leaderboard retrieved", "success", "leaderboards", leaderboards);
        }
        catch(ServiceRequestFailedException e)
        {
            response = ResponseUtils.buildResponse(e.getMessage(), "failed");
        }

        return response;
    }
}
