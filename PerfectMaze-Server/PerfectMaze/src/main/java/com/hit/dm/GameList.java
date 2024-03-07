package com.hit.dm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameList implements Serializable {
    private Map<String, Game> gameList;

    public GameList() {
        this.gameList = new HashMap<>();
    }

    public GameList(Map<String, Game> gameList) {
        this.gameList = gameList;
    }

    public Game getGameFromList(String gameId) {
        return gameList.get(gameId);
    }

    public void saveGameToList(Game game) {
        gameList.put(game.getGameId(), game);
    }

    public void removeGameFromList(String gameId) {
        gameList.remove(gameId);
    }

    public List<Game> getGameList() {
        return new ArrayList<>(gameList.values());
    }
}
