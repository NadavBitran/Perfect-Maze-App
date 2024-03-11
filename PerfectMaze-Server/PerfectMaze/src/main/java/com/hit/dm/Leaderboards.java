package com.hit.dm;

import java.util.List;

public class Leaderboards {
    private List<LeaderboardsEntity> leaderboards;

    public Leaderboards(List<LeaderboardsEntity> leaderboards) {
        this.leaderboards = leaderboards;
    }

    public LeaderboardsEntity getTopUser() {
        return leaderboards.get(0);
    }

    public LeaderboardsEntity getUserByLocation(int location) {
        return leaderboards.get(location);
    }
}
