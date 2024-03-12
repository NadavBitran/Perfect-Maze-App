package com.hit.controller;

import com.hit.dao.Dao;
import com.hit.dm.Leaderboards;
import com.hit.exceptions.ControllerRoutingFailed;
import com.hit.service.GameService;
import com.hit.service.LeaderboardService;
import com.hit.service.UserService;
import com.hit.service.util.ServicesContainer;

public class ControllerFactory {
    public static IController createController(String controllerType) throws ControllerRoutingFailed {
        switch (controllerType) {
            case "Leaderboards":
                return new LeaderboardsController(ServicesContainer.getLeaderboardService());
            case "User":
                return new UserController(ServicesContainer.getUserService());
            case "Game":
                return new GameController(ServicesContainer.getGameService());
            default:
                throw new ControllerRoutingFailed("Failed to route to controller type: " + controllerType);
        }
    }
}
