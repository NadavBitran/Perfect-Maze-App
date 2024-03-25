package com.hit.service;

import com.hit.algorithm.DFS;
import com.hit.algorithm.IShortestPaths;
import com.hit.constants.LocalRepositoryFileLocation;
import com.hit.dao.Dao;
import com.hit.dm.*;
import com.hit.exceptions.ServiceRequestFailed;
import com.hit.util.UndirectedGraphCreator;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Random;

import static com.hit.service.UtilTest.*;

public class LeaderboardServiceTest implements IServiceTest{
    private static final int TEST_USER_GAMES_COUNT = 4;
    private static final int TEST_MAZE_SIZE = 15;
    private String validUserId;
    private static GameService gameServiceTest;
    private static UserService userServiceTest;
    private static LeaderboardService leaderboardServiceTest;
    private static IShortestPaths<Integer> algorithm;
    private Leaderboards leaderboardsTest;


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

        leaderboardServiceTest = new LeaderboardService(
                new Dao<>(LocalRepositoryFileLocation.USER_FILE_LOCATION),
                new Dao<>(LocalRepositoryFileLocation.GAME_FILE_LOCATION));

        algorithm = new DFS<>(UndirectedGraphCreator.createNxNGridGraph(TEST_MAZE_SIZE));
    }


    @Before
    public void setup() throws ServiceRequestFailed {

        validUserId = userServiceTest.register(VALID_USER_EMAIL, VALID_USER_PASSWORD, VALID_USER_USERNAME);

        addFakeGamesToRegisteredUser();

        leaderboardsTest = leaderboardServiceTest.getLeaderboards();
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


    @Override
    @Test
    public void checkEntityAdditionSuccess() throws ServiceRequestFailed {
        gameServiceTest.saveGame(
                new Game(gameServiceTest.generateMaze(TEST_MAZE_SIZE, algorithm, TEST_ALGORITHM), 100, validUserId, VALID_USER_EMAIL));

        leaderboardsTest = leaderboardServiceTest.getLeaderboards();

        Assert.assertEquals(validUserId, leaderboardsTest.getTopUser().getUserId());
        Assert.assertEquals(TEST_USER_GAMES_COUNT + 1, leaderboardsTest.getTopUser().getGamesCount());
    }

    @Override
    @Test
    public void checkEntityRetrievalSuccess() throws ServiceRequestFailed {
        Assert.assertEquals(validUserId, leaderboardsTest.getTopUser().getUserId());
        Assert.assertEquals(TEST_USER_GAMES_COUNT, leaderboardsTest.getTopUser().getGamesCount());
    }

    @Override
    @Test(expected = IndexOutOfBoundsException.class)
    public void checkEntityRetrievalFailure() throws ServiceRequestFailed {
        Assert.assertNull(leaderboardsTest.getUserByLocation(1));
    }

    @Override
    @Test(expected = IndexOutOfBoundsException.class)
    public void checkEntityDeletionSuccess() throws ServiceRequestFailed {
        userServiceTest.deleteUser(VALID_USER_EMAIL, validUserId);

        leaderboardsTest = leaderboardServiceTest.getLeaderboards();

        Assert.assertNull(leaderboardsTest.getTopUser());
    }


    private void addFakeGamesToRegisteredUser() throws ServiceRequestFailed {
        Random random = new Random();
        int minTimeToSolve = 1;

        for (int i = 0; i < TEST_USER_GAMES_COUNT; i++)
        {
            int timeToSolve = random.nextInt(100) + minTimeToSolve;
            gameServiceTest.saveGame(new Game(gameServiceTest.generateMaze(TEST_MAZE_SIZE, algorithm, TEST_ALGORITHM), timeToSolve, validUserId, VALID_USER_EMAIL));
        }
    }
}
