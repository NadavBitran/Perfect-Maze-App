package com.example.perfectmazeclient.dm;

public class LeaderboardEntry {

    private String username;
    private int mazesCompleted;

    public LeaderboardEntry(String username, int mazesCompleted) {
        this.username = username;
        this.mazesCompleted = mazesCompleted;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMazesCompleted() {
        return mazesCompleted;
    }

    public void setMazesCompleted(int mazesCompleted) {
        this.mazesCompleted = mazesCompleted;
    }
}
