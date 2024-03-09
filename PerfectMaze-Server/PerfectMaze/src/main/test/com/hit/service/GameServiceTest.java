package com.hit.service;

import com.hit.algorithm.DFS;
import com.hit.algorithm.IShortestPaths;
import com.hit.dm.Game;
import com.hit.dm.PerfectMazeBoard;
import com.hit.util.UndirectedGraphCreator;
import org.junit.*;

import java.io.File;
import java.util.List;

public class GameServiceTest implements IServiceTest {

    private static final int MAZE_CHOSEN_SIZE = 10;
    private static final int MAZE_CHOSEN_SIZE_TOO_SMALL = 1;
    private static final int TIME_TO_SOLVE_MAZE = 105;
    private static final int TIME_TO_SOLVE_MAZE_IMPROVED = 100;
    private static final int TIME_TO_SOLVE_MAZE_IMPROVED_ERROR = 110;
    private static final String VALID_USER_ID = "USER_ID_TEST";
    private static GameService gameServiceTest = null;
    private static IShortestPaths<Integer> shortestPathTestAlgorithm = new DFS<>(UndirectedGraphCreator.createNxNGridGraph(MAZE_CHOSEN_SIZE));
    private static PerfectMazeBoard generatedMaze = null;

    @Test
    public void checkMazeGenerationSuccess()
    {
        generatedMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE);

        Assert.assertNotNull(generatedMaze);
        Assert.assertEquals(MAZE_CHOSEN_SIZE * 2 + 1 , generatedMaze.getRowsWithWalls());
        Assert.assertEquals(MAZE_CHOSEN_SIZE * 2 + 1, generatedMaze.getColsWithWalls());
    }

    @Test
    public void checkMazeGenerationFailure()
    {
        generatedMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE_TOO_SMALL);

        Assert.assertNull(generatedMaze);
    }

    @Override
    @Test
    public void checkEntityAdditionSuccess()
    {
        generatedMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE);

        Game newGame = new Game(generatedMaze, TIME_TO_SOLVE_MAZE, UtilTest.INVALID_USER_ID);

        Assert.assertTrue(gameServiceTest.saveGame(newGame));
    }

    @Override
    @Test
    public void checkEntityAdditionFailure()
    {
        // should be implemented after UserService created -> when userId doesn't exist in users DB
    }

    @Override
    @Test
    public void checkEntityRetrievalSuccess()
    {
        generatedMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE);

        Game newGame = new Game(generatedMaze, TIME_TO_SOLVE_MAZE, VALID_USER_ID);

        gameServiceTest.saveGame(newGame);

        Game retrievedGame = gameServiceTest.getGame(VALID_USER_ID, newGame.getGameId());

        Assert.assertEquals(newGame, retrievedGame);
    }

    @Override
    @Test
    public void checkEntityRetrievalFailure()
    {
        generatedMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE);

        Game newGame = new Game(generatedMaze, TIME_TO_SOLVE_MAZE, VALID_USER_ID);

        gameServiceTest.saveGame(newGame);

        Assert.assertNull(gameServiceTest.getGame(VALID_USER_ID, UtilTest.INVALID_GAME_ID));
        Assert.assertNull(gameServiceTest.getGame(UtilTest.INVALID_USER_ID, newGame.getGameId()));
    }

    @Override
    @Test
    public void checkEntityDeletionSuccess()
    {
        generatedMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE);

        Game newGame = new Game(generatedMaze, TIME_TO_SOLVE_MAZE, VALID_USER_ID);

        gameServiceTest.saveGame(newGame);

        Assert.assertTrue(gameServiceTest.deleteGame(VALID_USER_ID, newGame.getGameId()));
    }

    @Override
    @Test
    public void checkEntityDeletionFailure()
    {
        generatedMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE);

        Game newGame = new Game(generatedMaze, TIME_TO_SOLVE_MAZE, VALID_USER_ID);

        gameServiceTest.saveGame(newGame);

        Assert.assertFalse(gameServiceTest.deleteGame(VALID_USER_ID, UtilTest.INVALID_GAME_ID));
        Assert.assertFalse(gameServiceTest.deleteGame(UtilTest.INVALID_USER_ID, newGame.getGameId()));
    }

    @Test
    public void checkGameListRetrievalSuccess()
    {
        PerfectMazeBoard firstMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE);

        Game newGame1 = new Game(firstMaze, TIME_TO_SOLVE_MAZE, VALID_USER_ID);

        gameServiceTest.saveGame(newGame1);

        PerfectMazeBoard secondMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE);

        Game newGame2 = new Game(secondMaze, TIME_TO_SOLVE_MAZE, VALID_USER_ID);

        gameServiceTest.saveGame(newGame2);

        List<Game> gamesOfUser = gameServiceTest.getAllGamesOfUser(VALID_USER_ID);

        Assert.assertNotNull(gamesOfUser);
        Assert.assertEquals(2, gamesOfUser.size());
    }

    @Test
    public void checkGameListRetrievalFailure()
    {
        List<Game> gamesOfUser = gameServiceTest.getAllGamesOfUser(UtilTest.INVALID_USER_ID);

        Assert.assertNull(gamesOfUser);
    }

    @Test
    public void checkUpdateGetTimeImprovementSuccess()
    {
        generatedMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE);

        Game newGame = new Game(generatedMaze, TIME_TO_SOLVE_MAZE, VALID_USER_ID);

        gameServiceTest.saveGame(newGame);

        Assert.assertTrue(gameServiceTest.updateGameTimeImprovement(VALID_USER_ID, newGame.getGameId(), TIME_TO_SOLVE_MAZE_IMPROVED));
        Assert.assertTrue(gameServiceTest.getGame(VALID_USER_ID, newGame.getGameId()).getTimeToSolve() == TIME_TO_SOLVE_MAZE_IMPROVED);
    }

    @Test
    public void checkUpdateGetTimeImprovementFailure()
    {
        generatedMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE);

        Game newGame = new Game(generatedMaze, TIME_TO_SOLVE_MAZE, VALID_USER_ID);

        gameServiceTest.saveGame(newGame);

        Assert.assertFalse(gameServiceTest.updateGameTimeImprovement(VALID_USER_ID, newGame.getGameId(), TIME_TO_SOLVE_MAZE_IMPROVED_ERROR));
    }


    @Before
    public void checkIfServiceInitialized()
    {
        gameServiceTest = new GameService(UtilTest.GAME_TEST_FILE, shortestPathTestAlgorithm);

        File file = new File(UtilTest.GAME_TEST_FILE);
        Assert.assertTrue(file.exists());
    }
    @After
    public void checkIfTestFileDeleted()
    {
        File file = new File(UtilTest.GAME_TEST_FILE);
        file.delete();
        Assert.assertFalse(file.exists());
    }
}
