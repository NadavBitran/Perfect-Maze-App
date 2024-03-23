package com.example.perfectmazeclient.dm;

import java.util.List;

public class Leaderboards {
    private List<LeaderboardsEntity> leaderboards;

    public Leaderboards() {}
    public Leaderboards(List<LeaderboardsEntity> leaderboards) {
        this.leaderboards = leaderboards;
    }

    public LeaderboardsEntity getTopUser() {
        return leaderboards.get(0);
    }

    public LeaderboardsEntity getUserByLocation(int location) {
        return leaderboards.get(location);
    }

    public List<LeaderboardsEntity> getLeaderboards() {
        return leaderboards;
    }

    public void setLeaderboards(List<LeaderboardsEntity> leaderboards) {
        this.leaderboards = leaderboards;
    }
}