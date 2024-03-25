package com.example.perfectmazeclient.containers;

import com.example.perfectmazeclient.dm.Game;

public class CurrentGameContainer {
    private static Game currentGame;
    private static boolean isTryingToImprove;
    private static int timeImprovement;

    private CurrentGameContainer() {}

    public static Game getCurrentGame() {
        if(currentGame == null) {
            currentGame = new Game();
        }
        return currentGame;
    }

    public static boolean isTryingToImprove() {
        return isTryingToImprove;
    }

    public static void setCurrentGame(Game game) {
        currentGame = game;
    }

    public static void setIsTryingToImprove(boolean isTryingToImprove) {
        CurrentGameContainer.isTryingToImprove = isTryingToImprove;
    }

    public static int getTimeImprovement() {
        return timeImprovement;
    }

    public static void setTimeImprovement(int newTimeImprovement) {
        timeImprovement = newTimeImprovement;
    }
}
