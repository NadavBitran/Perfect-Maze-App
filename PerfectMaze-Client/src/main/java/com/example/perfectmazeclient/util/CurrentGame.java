package com.example.perfectmazeclient.util;

import com.example.perfectmazeclient.dm.Game;

public class CurrentGame {
    private static Game currentGame;
    private static boolean isTryingToImprove;

    private CurrentGame() {}

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static boolean isTryingToImprove() {
        return isTryingToImprove;
    }

    public static void setCurrentGame(Game game) {
        currentGame = game;
    }

    public static void setIsTryingToImprove(boolean isTryingToImprove) {
        CurrentGame.isTryingToImprove = isTryingToImprove;
    }
}
