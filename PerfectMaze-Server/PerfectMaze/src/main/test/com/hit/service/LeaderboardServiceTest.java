package com.hit.service;

import com.hit.algorithm.DFS;
import com.hit.algorithm.IShortestPaths;
import com.hit.dao.Dao;
import com.hit.dm.*;
import com.hit.exceptions.ServiceRequestFailed;
import com.hit.util.UndirectedGraphCreator;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.File;

public class LeaderboardServiceTest implements IServiceTest{
    private static final String VALID_USER_PASSWORD = "USER_PASSWORD_TEST";
    private static final String VALID_USER_EMAIL = "USER_EMAIL_TEST";
    private static final String VALID_USER_USERNAME = "USER_USERNAME_TEST";
    private static String VALID_USER_ID = null;
    private GameService gameServiceTest = null;
    private UserService userServiceTest = null;

    private static Dao<GameList> gameListDao = null;
    private static Dao<User> userDao = null;
    private static IShortestPaths<Integer> algorithm = new DFS<>(UndirectedGraphCreator.createNxNGridGraph(10));
    private LeaderboardService leaderboardServiceTest = null;
    private Leaderboards leaderboardsTest = null;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @Override
    @Test
    public void checkEntityAdditionSuccess() throws ServiceRequestFailed {
        gameServiceTest.saveGame(new Game(gameServiceTest.generateMaze(10, algorithm, "DFS"), 100, VALID_USER_ID, VALID_USER_EMAIL));

        leaderboardsTest = leaderboardServiceTest.getLeaderboards();

        Assert.assertEquals(VALID_USER_ID, leaderboardsTest.getTopUser().getUserId());
        Assert.assertEquals(5, leaderboardsTest.getTopUser().getGamesCount());
    }

    @Override
    @Test
    public void checkEntityRetrievalSuccess() throws ServiceRequestFailed {
        Assert.assertEquals(VALID_USER_ID, leaderboardsTest.getTopUser().getUserId());
        Assert.assertEquals(4, leaderboardsTest.getTopUser().getGamesCount());
    }

    @Override
    @Test(expected = IndexOutOfBoundsException.class)
    public void checkEntityRetrievalFailure() throws ServiceRequestFailed {
        Assert.assertNull(leaderboardsTest.getUserByLocation(1));
    }

    @Override
    @Test(expected = IndexOutOfBoundsException.class)
    public void checkEntityDeletionSuccess() throws ServiceRequestFailed {
        userServiceTest.deleteUser(VALID_USER_EMAIL, VALID_USER_ID);

        leaderboardsTest = leaderboardServiceTest.getLeaderboards();

        Assert.assertNull(leaderboardsTest.getTopUser());
    }

    @Before
    public void setup() throws ServiceRequestFailed {

        gameListDao = new Dao<>(UtilTest.GAME_TEST_FILE);

        userDao = new Dao<>(UtilTest.USER_TEST_FILE);

        gameServiceTest = new GameService(userDao, gameListDao);

        File gameFile = new File(UtilTest.GAME_TEST_FILE);

        Assert.assertTrue(gameFile.exists());

        userServiceTest = new UserService(userDao, gameListDao);

        File userFile = new File(UtilTest.USER_TEST_FILE);

        Assert.assertTrue(userFile.exists());

        leaderboardServiceTest = new LeaderboardService(userDao, gameListDao);

        VALID_USER_ID = userServiceTest.register(VALID_USER_EMAIL, VALID_USER_PASSWORD, VALID_USER_USERNAME);

        addFakeGamesToRegisteredUser();

        leaderboardsTest = leaderboardServiceTest.getLeaderboards();
    }


    @After
    public void teardown() {
        File gameFile = new File(UtilTest.GAME_TEST_FILE);
        gameFile.delete();
        Assert.assertFalse(gameFile.exists());

        File userFile = new File(UtilTest.USER_TEST_FILE);
        userFile.delete();
        Assert.assertFalse(userFile.exists());
    }

    private void addFakeGamesToRegisteredUser() throws ServiceRequestFailed {
        gameServiceTest.saveGame(new Game(gameServiceTest.generateMaze(10, algorithm, "DFS"), 100, VALID_USER_ID, VALID_USER_EMAIL));
        gameServiceTest.saveGame(new Game(gameServiceTest.generateMaze(10, algorithm, "DFS"), 90, VALID_USER_ID, VALID_USER_EMAIL));
        gameServiceTest.saveGame(new Game(gameServiceTest.generateMaze(10, algorithm, "DFS"), 80, VALID_USER_ID, VALID_USER_EMAIL));
        gameServiceTest.saveGame(new Game(gameServiceTest.generateMaze(10, algorithm, "DFS"), 100, VALID_USER_ID, VALID_USER_EMAIL));
    }
}
