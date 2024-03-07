package com.hit.service;

import com.hit.algorithm.DFS;
import com.hit.dao.Dao;
import com.hit.dm.Game;
import com.hit.dm.GameList;
import com.hit.undirectedGraph.UndirectedGraph;
import com.hit.dm.PerfectMazeBoard;
import com.hit.util.PerfectMazeGenerator;
import com.hit.util.UndirectedGraphCreator;

import java.util.List;

/**
 * This class provides services related to the Game entity, including saving, deleting, and retrieving games,
 * updating game time improvements, and generating perfect maze boards.
 */
public class GameService {

    private Dao<GameList> gameDao;

    /**
     * Constructs a GameService with a default Dao instance.
     */
    public GameService() {
        this.gameDao = new Dao<>("games.txt");
    }

    /**
     * Saves the provided game into the data storage.
     *
     * @param gamePlayed The game to be saved.
     * @return {@code true} if the game was successfully saved, {@code false} otherwise.
     */
    public boolean saveGame(Game gamePlayed) {
        GameList gamesPlayedByUser = gameDao.find(gamePlayed.getUserId());

        if(gamesPlayedByUser == null) gamesPlayedByUser = new GameList();

        gamesPlayedByUser.saveGameToList(gamePlayed);

        return gameDao.save(gamePlayed.getUserId(), gamesPlayedByUser);
    }

    /**
     * Deletes the game identified by the provided game ID from the data storage.
     *
     * @param gameId The ID of the game to be deleted.
     * @param userId The unique identifier of the user associated with the game.
     * @return {@code true} if the game was successfully deleted, {@code false} otherwise.
     */
    public boolean deleteGame(String userId, String gameId) {
        GameList gamesPlayedByUser = gameDao.find(userId);

        if(gamesPlayedByUser == null) return false;

        gamesPlayedByUser.removeGameFromList(gameId);

        return gameDao.save(userId, gamesPlayedByUser);
    }

    /**
     * Retrieves the game identified by the provided game ID.
     *
     * @param gameId The ID of the game to be retrieved.
     * @param userId The unique identifier of the user associated with the game.
     * @return The game identified by the provided game ID, or {@code null} if not found.
     */
    public Game getGame(String userId, String gameId) {
        GameList gamesPlayedByUser = gameDao.find(userId);

        if(gamesPlayedByUser == null) return null;

        return gamesPlayedByUser.getGameFromList(gameId);
    }

    /**
     * Retrieves all games played by the user identified by the provided user ID.
     *
     * @param userId The unique identifier of the user associated with the games.
     * @return A list of all games played by the user, or {@code null} if the user has not played any games.
     */
    public List<Game> getAllGamesOfUser(String userId) {
        GameList gamesPlayedByUser = gameDao.find(userId);

        if(gamesPlayedByUser == null) return null;

        return gamesPlayedByUser.getGameList();
    }


    /**
     * Updates the time improvement for the game identified by the provided game ID.
     *
     * @param gameId The ID of the game to be updated.
     * @param userId The unique identifier of the user associated with the game.
     * @param newTime The new time value for the game.
     * @return {@code true} if the time improvement was successfully updated, {@code false} otherwise.
     */
    public boolean updateGameTimeImprovement(String userId, String gameId, int newTime) {
        GameList gamesPlayedByUser = gameDao.find(userId);

        if(gamesPlayedByUser == null) return false;

        Game game = gamesPlayedByUser.getGameFromList(gameId);

        if (game == null) return false;

        if (game.getTimeToSolve() < newTime) return false;

        game.setTimeToSolve(newTime);

        gamesPlayedByUser.saveGameToList(game);

        return gameDao.save(userId, gamesPlayedByUser);
    }

    /**
     * Generates a perfect maze board of the specified size using DFS algorithm.
     *
     * @param mazeSize The size of the maze board to be generated.
     * @return A perfect maze board of the specified size, or {@code null} if the size is invalid.
     */
    public PerfectMazeBoard generateMaze(int mazeSize) {
        if (!PerfectMazeGenerator.isMazeSizeValid(mazeSize)) return null;

        UndirectedGraph<Integer> gridGraph = UndirectedGraphCreator.createNxNGridGraph(mazeSize);

        DFS<Integer> dfs = new DFS<>(gridGraph);

        UndirectedGraph<Integer> spanningTree = dfs.run();

        return PerfectMazeGenerator.generateMazeFromSpanningTree(spanningTree, mazeSize);
    }

}
