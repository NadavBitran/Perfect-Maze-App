package com.hit.service;
import com.hit.algorithm.DFS;
import com.hit.algorithm.IShortestPaths;
import com.hit.dao.Dao;
import com.hit.dm.Game;
import com.hit.dm.GameList;
import com.hit.dm.User;
import com.hit.undirectedGraph.UndirectedGraph;
import com.hit.dm.PerfectMazeBoard;
import com.hit.util.PerfectMazeGenerator;
import com.hit.util.ServiceRequestFailedException;
import com.hit.util.UndirectedGraphCreator;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * This class provides services related to the Game entity, including saving, deleting, and retrieving games,
 * updating game time improvements, and generating perfect maze boards.
 */
public class GameService {

    private Dao<GameList> gameDao;
    private IShortestPaths<Integer> shortestPathAlgorithm;

    /**
     * Constructs a GameService with a default Dao instance.
     */

    public GameService(String filePathGame) {
        this.gameDao = new Dao<>(filePathGame);
    }

    public GameService(String filePathGame, IShortestPaths<Integer> shortestPathAlgorithm) {
        this.gameDao = new Dao<>(filePathGame);
        this.shortestPathAlgorithm = shortestPathAlgorithm;
    }

    /**
     * Saves the provided game into the data storage.
     *
     * @param gamePlayed The game to be saved.
     * @return {@code true} if the game was successfully saved, {@code false} otherwise.
     */
    public void saveGame(Game gamePlayed) throws ServiceRequestFailedException {
        GameList gamesPlayedByUser = gameDao.find(gamePlayed.getUserId());

        if(gamesPlayedByUser == null) gamesPlayedByUser = new GameList(gamePlayed.getUserId(), gamePlayed.getUserEmail());

        gamesPlayedByUser.saveGameToList(gamePlayed);

        gameDao.save(gamePlayed.getUserId(), gamesPlayedByUser);
    }

    /**
     * Deletes the game identified by the provided game ID from the data storage.
     *
     * @param gameId The ID of the game to be deleted.
     * @param userId The unique identifier of the user associated with the game.
     * @return {@code true} if the game was successfully deleted, {@code false} otherwise.
     */
    public void deleteGame(String userId, String gameId) throws ServiceRequestFailedException  {
        GameList gamesPlayedByUser = gameDao.find(userId);

        if(gamesPlayedByUser == null)
            throw new ServiceRequestFailedException("Failed to delete game with id: " + gameId + ": user with id: " + userId + " did not played yet");

        if(!gamesPlayedByUser.removeGameFromList(gameId))
            throw new ServiceRequestFailedException("Failed to delete game with id: " + gameId + ": game with id: " + gameId + " not exist");

        gameDao.save(userId, gamesPlayedByUser);
    }

    /**
     * Retrieves the game identified by the provided game ID.
     *
     * @param gameId The ID of the game to be retrieved.
     * @param userId The unique identifier of the user associated with the game.
     * @return The game identified by the provided game ID, or {@code null} if not found.
     */
    public Game getGame(String userId, String gameId) throws ServiceRequestFailedException {
        GameList gamesPlayedByUser = gameDao.find(userId);

        if(gamesPlayedByUser == null)
            throw new ServiceRequestFailedException("Failed to get game with id: " + gameId + ": user with id: " + userId + " did not played yet");

        Game game = gamesPlayedByUser.getGameFromList(gameId);

        if(game == null)
            throw new ServiceRequestFailedException("Failed to get game with id: " + gameId + ": game with id: " + gameId + " not exist");

        return game;
    }

    /**
     * Retrieves all games played by the user identified by the provided user ID.
     *
     * @param userId The unique identifier of the user associated with the games.
     * @return A list of all games played by the user, or {@code null} if the user has not played any games.
     */
    public List<Game> getAllGamesOfUser(String userId) throws ServiceRequestFailedException {
        GameList gamesPlayedByUser = gameDao.find(userId);

        if(gamesPlayedByUser == null)
            throw new ServiceRequestFailedException("Failed to get games played by user with id: " + userId + " did not played yet");

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
    public void updateGameTimeImprovement(String userId, String gameId, int newTime) throws ServiceRequestFailedException {
        GameList gamesPlayedByUser = gameDao.find(userId);

        if(gamesPlayedByUser == null)
            throw new ServiceRequestFailedException("Failed to update game with id: " + gameId + ": user with id: " + userId + " did not played yet");

        Game game = gamesPlayedByUser.getGameFromList(gameId);

        if (game == null)
            throw new ServiceRequestFailedException("Failed to update game with id: " + gameId + ": game with id: " + gameId + " not exist");

        if (game.getTimeToSolve() < newTime)
            throw new ServiceRequestFailedException("Failed to update game with id: " + gameId + ": new time: " + newTime + " is not an improvement over the current time: " + game.getTimeToSolve());

        game.setTimeToSolve(newTime);

        gamesPlayedByUser.saveGameToList(game);

        gameDao.save(userId, gamesPlayedByUser);
    }

    /**
     * Generates a perfect maze board of the specified size using DFS algorithm.
     *
     * @param mazeSize The size of the maze board to be generated.
     * @return A perfect maze board of the specified size, or {@code null} if the size is invalid.
     */
    public PerfectMazeBoard generateMaze(int mazeSize) throws ServiceRequestFailedException {
        if (!PerfectMazeGenerator.isMazeSizeValid(mazeSize))
            throw new ServiceRequestFailedException("Failed to generate maze: maze size " + mazeSize + " is invalid");

        UndirectedGraph<Integer> spanningTree = shortestPathAlgorithm.run();

        return PerfectMazeGenerator.generateMazeFromSpanningTree(spanningTree, mazeSize);
    }

}
