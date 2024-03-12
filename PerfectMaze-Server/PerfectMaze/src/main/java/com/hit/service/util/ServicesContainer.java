package com.hit.service.util;

import com.hit.constants.LocalRepositoryFileLocation;
import com.hit.dao.Dao;
import com.hit.service.GameService;
import com.hit.service.LeaderboardService;
import com.hit.service.UserService;

public class ServicesContainer {
    private static GameService gameService = new GameService(new Dao<>(LocalRepositoryFileLocation.USER_FILE_LOCATION),
                                                             new Dao<>(LocalRepositoryFileLocation.GAME_FILE_LOCATION));
    private static UserService userService = new UserService(new Dao<>(LocalRepositoryFileLocation.USER_FILE_LOCATION),
                                                             new Dao<>(LocalRepositoryFileLocation.GAME_FILE_LOCATION));
    private static LeaderboardService leaderboardService = new LeaderboardService(new Dao<>(LocalRepositoryFileLocation.USER_FILE_LOCATION),
                                                                                  new Dao<>(LocalRepositoryFileLocation.GAME_FILE_LOCATION));

    public static GameService getGameService() { return gameService; }
    public static UserService getUserService() { return userService; }
    public static LeaderboardService getLeaderboardService() { return leaderboardService; }
}
