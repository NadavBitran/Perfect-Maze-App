package com.hit.server.util;

import com.hit.controller.GameController;
import com.hit.controller.IController;
import com.hit.controller.LeaderboardsController;
import com.hit.controller.UserController;

public class ControllersContainer {
    private static GameController gameController;
    private static UserController userController;
    private static LeaderboardsController leaderboardController;
    public static GameController getGameController() { return gameController; }
    public static UserController getUserController() { return userController; }
    public static LeaderboardsController getLeaderboardController() { return leaderboardController; }
    public static void setGameController(GameController gameController) { ControllersContainer.gameController = gameController; }
    public static void setUserController(UserController userController) { ControllersContainer.userController = userController; }
    public static void setLeaderboardController(LeaderboardsController leaderboardController) { ControllersContainer.leaderboardController = leaderboardController; }
}
