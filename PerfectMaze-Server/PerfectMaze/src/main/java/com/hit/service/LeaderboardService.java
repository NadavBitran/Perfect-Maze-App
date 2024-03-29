package com.hit.service;

import com.hit.dao.Dao;
import com.hit.dm.GameList;
import com.hit.dm.Leaderboards;
import com.hit.dm.User;
import com.hit.dm.LeaderboardsEntity;
import com.hit.exceptions.ServiceRequestFailed;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class LeaderboardService {

    private Dao<User> userDao;
    private Dao<GameList> gameDao;
    private static final int LARGEST_LEADERBOARDS_SIZE = 25;

    public LeaderboardService(Dao<User> userDao, Dao<GameList> gameDao)
    {
        this.gameDao = gameDao;
        this.userDao = userDao;
    }

    public Leaderboards getLeaderboards() throws ServiceRequestFailed {
        List<GameList> allGames = gameDao.findAll();

        allGames.sort(new Comparator<GameList>() {
            @Override
            public int compare(GameList o1, GameList o2) {
                return o1.getGamesCount() - o2.getGamesCount();
            }
        });

        List<LeaderboardsEntity> topUsers = new ArrayList<>();

        Map<String, User> users = userDao.getMap();

        int leaderboardsSize = Math.min(LARGEST_LEADERBOARDS_SIZE, allGames.size());

        for (int i=0;i<leaderboardsSize;i++) {
            User user = users.get(allGames.get(i).getUserEmail());
            if(user == null) continue;
            topUsers.add(new LeaderboardsEntity(
                    user.getUserId(),
                    user.getUsername(),
                    allGames.get(i).getGamesCount()));
        }

        return new Leaderboards(topUsers);
    }
}
