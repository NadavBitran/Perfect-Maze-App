package com.hit.controller;

import com.hit.exceptions.ControllerRoutingFailed;
import com.hit.server.util.ControllersContainer;

public class ControllerFactory {
    public static IController createController(String controllerType) throws ControllerRoutingFailed {
        switch (controllerType) {
            case "Leaderboards":
                return ControllersContainer.getLeaderboardController();
            case "User":
                return ControllersContainer.getUserController();
            case "Game":
                return ControllersContainer.getGameController();
            default:
                throw new ControllerRoutingFailed("Failed to route to controller type: " + controllerType);
        }
    }
}
