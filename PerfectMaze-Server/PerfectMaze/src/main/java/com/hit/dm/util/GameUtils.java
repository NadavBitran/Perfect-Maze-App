package com.hit.dm.util;

import com.hit.dm.Game;

public class GameUtils {
    public static boolean isGamePropertiesExistValid(Game game)
    {
        return game != null &&
                game.getTimeToSolve() > 0 &&
                game.getUserEmail() != null &&
                game.getUserId() != null &&
                PerfectMazeBoardUtils.isPerfectMazeBoardPropertiesExist(game.getMazeBoard());
    }
}
