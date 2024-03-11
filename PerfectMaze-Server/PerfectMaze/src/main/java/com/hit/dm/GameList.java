package com.hit.dm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameList implements Serializable {
    private Map<String, Game> gameList;
    private String userId;
    private String userEmail;

    public GameList() {
    }

    public GameList(String userId, String userEmail) {
        this.gameList = new HashMap<>();
        this.userId = userId;
        this.userEmail = userEmail;
    }

    public GameList(Map<String, Game> gameList, String userId, String userEmail) {
        this.gameList = gameList;
        this.userId = userId;
        this.userEmail = userEmail;
    }

    public Game getGameFromList(String gameId) {
        return gameList.get(gameId);
    }

    public void saveGameToList(Game game) {
        gameList.put(game.getGameId(), game);
    }

    public boolean removeGameFromList(String gameId) {
        return gameList.remove(gameId) != null;
    }

    public List<Game> getGameList() {
        return new ArrayList<>(gameList.values());
    }

    public int getGamesCount() {
        return gameList.size();
    }

    public String getUserId() {return userId;}
    public String getUserEmail() {return userEmail;}

}
