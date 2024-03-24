package com.hit.service;

import com.hit.algorithm.DFS;
import com.hit.algorithm.IShortestPaths;
import com.hit.constants.LocalRepositoryFileLocation;
import com.hit.dao.Dao;
import com.hit.dm.Game;
import com.hit.dm.GameList;
import com.hit.dm.PerfectMazeBoard;
import com.hit.dm.User;
import com.hit.exceptions.ServiceRequestFailed;
import com.hit.util.UndirectedGraphCreator;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

import static com.hit.service.UtilTest.*;

public class GameServiceTest implements IServiceTest {

    private static final int MAZE_CHOSEN_SIZE = 10;
    private static final int MAZE_CHOSEN_SIZE_TOO_SMALL = 1;
    private static final int TIME_TO_SOLVE_MAZE = 105;
    private static final int TIME_TO_SOLVE_MAZE_IMPROVED = 100;
    private static final int TIME_TO_SOLVE_MAZE_IMPROVED_ERROR = 110;
    private static GameService gameServiceTest;
    private static UserService userServiceTest;
    private static IShortestPaths<Integer> shortestPathTestAlgorithm;
    private static Game newGame;
    private static PerfectMazeBoard generatedMaze;
    private String validUserId;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();


    @BeforeClass
    public static void setupClass() {
        gameServiceTest = new GameService(
                new Dao<>(LocalRepositoryFileLocation.USER_FILE_LOCATION),
                new Dao<>(LocalRepositoryFileLocation.GAME_FILE_LOCATION));

        userServiceTest = new UserService(
                new Dao<>(LocalRepositoryFileLocation.USER_FILE_LOCATION),
                new Dao<>(LocalRepositoryFileLocation.GAME_FILE_LOCATION));

        shortestPathTestAlgorithm = new DFS(UndirectedGraphCreator.createNxNGridGraph(MAZE_CHOSEN_SIZE));
    }
    @Before
    public void setup() throws ServiceRequestFailed {
        validUserId = userServiceTest.register(VALID_USER_EMAIL, VALID_USER_PASSWORD, VALID_USER_USERNAME);

        generatedMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE, shortestPathTestAlgorithm, TEST_ALGORITHM);

        Game detailsOfGameToSave = new Game(generatedMaze, TIME_TO_SOLVE_MAZE, validUserId, VALID_USER_EMAIL);

        newGame = gameServiceTest.saveGame(detailsOfGameToSave);
    }

    @After
    public void teardown() throws IOException {
        ObjectOutputStream objectOutputStreamGame = new ObjectOutputStream(new FileOutputStream(LocalRepositoryFileLocation.GAME_FILE_LOCATION, false));
        objectOutputStreamGame.writeObject(new HashMap<String, GameList>());
        objectOutputStreamGame.close();

        ObjectOutputStream objectOutputStreamUser = new ObjectOutputStream(new FileOutputStream(LocalRepositoryFileLocation.USER_FILE_LOCATION, false));
        objectOutputStreamUser.writeObject(new HashMap<String, User>());
        objectOutputStreamUser.close();
    }

    @AfterClass
    public static void teardownClass() throws IOException {
        File file = new File(LocalRepositoryFileLocation.GAME_FILE_LOCATION);
        file.delete();

        Assert.assertFalse(file.exists());

        file = new File(LocalRepositoryFileLocation.USER_FILE_LOCATION);
        file.delete();

        Assert.assertFalse(file.exists());
    }


    @Test
    public void checkMazeGenerationSuccess() throws ServiceRequestFailed {
        generatedMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE, shortestPathTestAlgorithm, TEST_ALGORITHM);

        Assert.assertNotNull(generatedMaze);
        Assert.assertEquals(MAZE_CHOSEN_SIZE * 2 + 1, generatedMaze.getRowsWithWalls());
        Assert.assertEquals(MAZE_CHOSEN_SIZE * 2 + 1, generatedMaze.getColsWithWalls());
    }

    @Test(expected = ServiceRequestFailed.class)
    public void checkMazeGenerationFailure() throws ServiceRequestFailed {
        generatedMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE_TOO_SMALL, shortestPathTestAlgorithm, TEST_ALGORITHM);
    }

    @Override
    @Test
    public void checkEntityAdditionSuccess() throws ServiceRequestFailed {
        generatedMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE, shortestPathTestAlgorithm, TEST_ALGORITHM);

        Game detailsOfGameToSave = new Game(generatedMaze, TIME_TO_SOLVE_MAZE, validUserId, VALID_USER_EMAIL);

        Game savedGameInRepository = gameServiceTest.saveGame(detailsOfGameToSave);

        Game game = gameServiceTest.getGame(validUserId, savedGameInRepository.getGameId());

        Assert.assertEquals(game, savedGameInRepository);
    }

    @Test(expected = ServiceRequestFailed.class)
    public void checkEntityAdditionFailure() throws ServiceRequestFailed {
        generatedMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE, shortestPathTestAlgorithm, TEST_ALGORITHM);

        Game detailsOfGameToSave = new Game(generatedMaze, TIME_TO_SOLVE_MAZE, INVALID_USER_ID, INVALID_USER_EMAIL);

        gameServiceTest.saveGame(detailsOfGameToSave);
    }

    @Override
    @Test
    public void checkEntityRetrievalSuccess() throws ServiceRequestFailed {
        Game game = gameServiceTest.getGame(validUserId, newGame.getGameId());

        Assert.assertEquals(newGame, game);
    }

    @Override
    @Test(expected = ServiceRequestFailed.class)
    public void checkEntityRetrievalFailure() throws ServiceRequestFailed {
        gameServiceTest.getGame(validUserId, UtilTest.INVALID_GAME_ID);
    }

    @Override
    @Test(expected = ServiceRequestFailed.class)
    public void checkEntityDeletionSuccess() throws ServiceRequestFailed {
        gameServiceTest.deleteGame(validUserId, newGame.getGameId());

        gameServiceTest.getGame(validUserId, newGame.getGameId());
    }


    @Test(expected = ServiceRequestFailed.class)
    public void checkEntityDeletionFailure() throws ServiceRequestFailed {
        gameServiceTest.deleteGame(validUserId, UtilTest.INVALID_GAME_ID);
    }

    @Test
    public void checkGameListRetrievalSuccess() throws ServiceRequestFailed {

        PerfectMazeBoard secondMaze = gameServiceTest.generateMaze(MAZE_CHOSEN_SIZE, shortestPathTestAlgorithm, TEST_ALGORITHM);

        Game newGame2 = new Game(secondMaze, TIME_TO_SOLVE_MAZE, validUserId, VALID_USER_EMAIL);

        gameServiceTest.saveGame(newGame2);

        List<Game> gamesOfUser = gameServiceTest.getAllGamesOfUser(validUserId);

        Assert.assertNotNull(gamesOfUser);

        Assert.assertEquals(2, gamesOfUser.size());
    }

    @Test(expected = ServiceRequestFailed.class)
    public void checkGameListRetrievalFailure() throws ServiceRequestFailed {
        gameServiceTest.getAllGamesOfUser(UtilTest.INVALID_USER_ID);
    }

    @Test
    public void checkUpdateGetTimeImprovementSuccess() throws ServiceRequestFailed {
        gameServiceTest.updateGameTimeImprovement(validUserId, newGame.getGameId(), TIME_TO_SOLVE_MAZE_IMPROVED);

        Assert.assertEquals(gameServiceTest.getGame(validUserId, newGame.getGameId()).getTimeToSolve(), TIME_TO_SOLVE_MAZE_IMPROVED);
    }

    @Test(expected = ServiceRequestFailed.class)
    public void checkUpdateGetTimeImprovementFailure() throws ServiceRequestFailed {
        gameServiceTest.updateGameTimeImprovement(validUserId, newGame.getGameId(), TIME_TO_SOLVE_MAZE_IMPROVED_ERROR);
    }

}
