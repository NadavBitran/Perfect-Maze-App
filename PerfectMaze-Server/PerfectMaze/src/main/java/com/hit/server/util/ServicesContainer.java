package com.hit.server.util;


import com.hit.service.GameService;
import com.hit.service.LeaderboardService;
import com.hit.service.UserService;

public class ServicesContainer {
    private static GameService gameService;
    private static UserService userService;
    private static LeaderboardService leaderboardService;

    public static GameService getGameService() {
        return gameService;
    }

    public static void setGameService(GameService gameService) {
        ServicesContainer.gameService = gameService;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static void setUserService(UserService userService) {
        ServicesContainer.userService = userService;
    }

    public static LeaderboardService getLeaderboardService() {
        return leaderboardService;
    }

    public static void setLeaderboardService(LeaderboardService leaderboardService) {
        ServicesContainer.leaderboardService = leaderboardService;
    }
}
