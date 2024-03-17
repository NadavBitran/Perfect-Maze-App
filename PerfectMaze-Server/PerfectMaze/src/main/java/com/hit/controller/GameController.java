package com.hit.controller;
import com.hit.dm.Game;
import com.hit.dm.LeaderboardsEntity;

public class GameController {
    public void saveGame(Game game){}
    public void deleteGame(Game game){}
    public Game getGame(Long gameId){
        return new Game();
    }
    public LeaderboardsEntity getTopUser(){
        return new LeaderboardsEntity("123", "User", 1);
    }
    public LeaderboardsEntity getUserByLocation(int location){
        return new LeaderboardsEntity("123", "User", 1);
    }

}
