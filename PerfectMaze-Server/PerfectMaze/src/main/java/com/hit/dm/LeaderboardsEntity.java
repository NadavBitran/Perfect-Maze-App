package com.hit.dm;

public class LeaderboardsEntity {
    private String userId;
    private String username;
    private int gamesCount;

    public LeaderboardsEntity(String userId, String username , int gamesCount) {
        this.userId = userId;
        this.username = username;
        this.gamesCount = gamesCount;
    }

    public String getUserId() {
        return userId;
    }

    public int getGamesCount() {
        return gamesCount;
    }

    public String getUsername() {
        return username;
    }
}
