package com.hit.service;

import com.hit.algorithm.DFS;
import com.hit.algorithm.IShortestPaths;
import com.hit.dao.Dao;
import com.hit.dm.Game;
import com.hit.dm.GameList;
import com.hit.dm.PerfectMazeBoard;
import com.hit.dm.User;
import com.hit.exceptions.ServiceRequestFailedException;
import com.hit.util.UndirectedGraphCreator;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GameServiceTest implements IServiceTest {

    private static final int MAZE_CHOSEN_SIZE = 10;
    private static final int MAZE_CHOSEN_SIZE_TOO_SMALL = 1;
    private static final int TIME_TO_SOLVE_MAZE = 105;
    private static final int TIME_TO_SOLVE_MAZE_IMPROVED = 100;
    private static final int TIME_TO_SOLVE_MAZE_IMPROVED_ERROR = 110;
    private static String VALID_USER_ID = "USER_ID_TEST";
    private static final String VALID_USER_PASSWORD = "USER_PASSWORD_TEST";
    private static final String VALID_USER_USERNAME = "USER_USERNAME_TEST";
    private static final String VALID_USER_EMAIL = "USER_EMAIL_TEST";
    private static GameService gameServiceTest = null;
    private static UserService userServiceTest = null;
    private static Dao<GameList> gameListDao = null;
    private static Dao<User> userDao = null;
    private static final IShortestPaths<Integer> shortestPathTestAlgorithm = new DFS(UndirectedGraphCreator.createNxNGridGraph(MAZE_CHOSEN_SIZE));
    private static Game newGame = null;
    private static PerfectMazeBoard generatedMaze = null;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();


    @Before
    public void setup() throws ServiceRequestFailedException {
        gameListDao = new Dao<>(UtilTest.GAME_TEST_FILE);
        userDao = new Dao<>(UtilTest.USER_TEST_FILE);

        gameServiceTest = new GameService(userDao, gameListDao);
        userServiceTest = new UserService(userDao, gameListDao);

        VALID_USER_ID = userServiceTest.register(VALID_USER_EMAIL, VALID_USER_PASSWORD, VALID_USER_USERNAME);

        generatedMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE, shortestPathTestAlgorithm);

        Game detailsOfGameToSave = new Game(generatedMaze, TIME_TO_SOLVE_MAZE, VALID_USER_ID, VALID_USER_EMAIL);

        newGame = gameServiceTest.saveGame(detailsOfGameToSave);
    }

    @After
    public void teardown()  {

        File userFile = new File(UtilTest.USER_TEST_FILE);
        userFile.delete();
        Assert.assertFalse(userFile.exists());

        File gameFile = new File(UtilTest.GAME_TEST_FILE);
        gameFile.delete();
        Assert.assertFalse(gameFile.exists());
    }

    @Test
    public void checkMazeGenerationSuccess() throws ServiceRequestFailedException {
        generatedMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE, shortestPathTestAlgorithm);

        Assert.assertNotNull(generatedMaze);
        Assert.assertEquals(MAZE_CHOSEN_SIZE * 2 + 1, generatedMaze.getRowsWithWalls());
        Assert.assertEquals(MAZE_CHOSEN_SIZE * 2 + 1, generatedMaze.getColsWithWalls());
    }

    @Test(expected = ServiceRequestFailedException.class)
    public void checkMazeGenerationFailure() throws ServiceRequestFailedException {
        generatedMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE_TOO_SMALL, shortestPathTestAlgorithm);
    }

    @Override
    @Test
    public void checkEntityAdditionSuccess() throws ServiceRequestFailedException {
        generatedMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE, shortestPathTestAlgorithm);

        Game detailsOfGameToSave = new Game(generatedMaze, TIME_TO_SOLVE_MAZE, VALID_USER_ID, VALID_USER_EMAIL);

        Game savedGameInRepository = gameServiceTest.saveGame(detailsOfGameToSave);

        Game game = gameServiceTest.getGame(VALID_USER_ID, savedGameInRepository.getGameId());

        Assert.assertEquals(game, savedGameInRepository);
    }

    @Override
    @Test
    public void checkEntityRetrievalSuccess() throws ServiceRequestFailedException {
        Game game = gameServiceTest.getGame(VALID_USER_ID, newGame.getGameId());

        Assert.assertEquals(newGame, game);
    }

    @Override
    @Test(expected = ServiceRequestFailedException.class)
    public void checkEntityRetrievalFailure() throws ServiceRequestFailedException {
        gameServiceTest.getGame(VALID_USER_ID, UtilTest.INVALID_GAME_ID);
    }

    @Override
    @Test(expected = ServiceRequestFailedException.class)
    public void checkEntityDeletionSuccess() throws ServiceRequestFailedException {
        gameServiceTest.deleteGame(VALID_USER_ID, newGame.getGameId());

        gameServiceTest.getGame(VALID_USER_ID, newGame.getGameId());
    }


    @Test(expected = ServiceRequestFailedException.class)
    public void checkEntityDeletionFailure() throws ServiceRequestFailedException {
        gameServiceTest.deleteGame(VALID_USER_ID, UtilTest.INVALID_GAME_ID);
    }

    @Test
    public void checkGameListRetrievalSuccess() throws ServiceRequestFailedException {

        PerfectMazeBoard secondMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE, shortestPathTestAlgorithm);

        Game newGame2 = new Game(secondMaze, TIME_TO_SOLVE_MAZE, VALID_USER_ID, VALID_USER_EMAIL);

        gameServiceTest.saveGame(newGame2);

        List<Game> gamesOfUser = gameServiceTest.getAllGamesOfUser(VALID_USER_ID);

        Assert.assertNotNull(gamesOfUser);

        Assert.assertEquals(2, gamesOfUser.size());
    }

    @Test(expected = ServiceRequestFailedException.class)
    public void checkGameListRetrievalFailure() throws ServiceRequestFailedException {
        gameServiceTest.getAllGamesOfUser(UtilTest.INVALID_USER_ID);
    }

    @Test
    public void checkUpdateGetTimeImprovementSuccess() throws ServiceRequestFailedException {
        gameServiceTest.updateGameTimeImprovement(VALID_USER_ID, newGame.getGameId(), TIME_TO_SOLVE_MAZE_IMPROVED);

        Assert.assertEquals(gameServiceTest.getGame(VALID_USER_ID, newGame.getGameId()).getTimeToSolve(), TIME_TO_SOLVE_MAZE_IMPROVED);
    }

    @Test(expected = ServiceRequestFailedException.class)
    public void checkUpdateGetTimeImprovementFailure() throws ServiceRequestFailedException {
        gameServiceTest.updateGameTimeImprovement(VALID_USER_ID, newGame.getGameId(), TIME_TO_SOLVE_MAZE_IMPROVED_ERROR);
    }

}
