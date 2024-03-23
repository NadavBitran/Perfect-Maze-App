package com.example.perfectmazeclient.dm;

public class LeaderboardsEntity {

    private String username;
    private int gamesCount;

    public LeaderboardsEntity(String username, int gamesCount) {
        this.username = username;
        this.gamesCount = gamesCount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGamesCount() {
        return gamesCount;
    }

    public void setGamesCount(int gamesCount) {
        this.gamesCount = gamesCount;
    }
}
