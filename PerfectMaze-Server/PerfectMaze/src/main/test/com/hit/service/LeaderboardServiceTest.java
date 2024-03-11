package com.hit.service;

import com.hit.algorithm.DFS;
import com.hit.dm.Game;
import com.hit.dm.Leaderboards;
import com.hit.dm.LeaderboardsEntity;
import com.hit.util.ServiceRequestFailedException;
import com.hit.util.UndirectedGraphCreator;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.util.List;

public class LeaderboardServiceTest implements IServiceTest{
    private static final String VALID_USER_PASSWORD = "USER_PASSWORD_TEST";
    private static final String VALID_USER_EMAIL = "USER_EMAIL_TEST";
    private static final String VALID_USER_USERNAME = "USER_USERNAME_TEST";
    private static String VALID_USER_ID = null;
    private GameService gameServiceTest = null;
    private UserService userServiceTest = null;
    private LeaderboardService leaderboardServiceTest = null;
    private Leaderboards leaderboardsTest = null;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @Override
    @Test
    public void checkEntityAdditionSuccess() throws ServiceRequestFailedException {
        gameServiceTest.saveGame(new Game(gameServiceTest.generateMaze(10), 100, VALID_USER_ID, VALID_USER_EMAIL));

        leaderboardsTest = leaderboardServiceTest.getLeaderboards();

        Assert.assertEquals(VALID_USER_ID, leaderboardsTest.getTopUser().getUserId());
        Assert.assertEquals(5, leaderboardsTest.getTopUser().getGamesCount());
    }

    @Override
    @Test
    public void checkEntityRetrievalSuccess() throws ServiceRequestFailedException {
        Assert.assertEquals(VALID_USER_ID, leaderboardsTest.getTopUser().getUserId());
        Assert.assertEquals(4, leaderboardsTest.getTopUser().getGamesCount());
    }

    @Override
    @Test(expected = IndexOutOfBoundsException.class)
    public void checkEntityRetrievalFailure() throws ServiceRequestFailedException {
        Assert.assertNull(leaderboardsTest.getUserByLocation(1));
    }

    @Override
    @Test(expected = IndexOutOfBoundsException.class)
    public void checkEntityDeletionSuccess() throws ServiceRequestFailedException {
        userServiceTest.deleteUser(VALID_USER_EMAIL, VALID_USER_ID);

        leaderboardsTest = leaderboardServiceTest.getLeaderboards();

        Assert.assertNull(leaderboardsTest.getTopUser());
    }

    @Before
    public void setup() throws ServiceRequestFailedException {
        gameServiceTest = new GameService(UtilTest.GAME_TEST_FILE, new DFS<Integer>(UndirectedGraphCreator.createNxNGridGraph(10)));

        File gameFile = new File(UtilTest.GAME_TEST_FILE);

        Assert.assertTrue(gameFile.exists());

        userServiceTest = new UserService(UtilTest.USER_TEST_FILE, UtilTest.GAME_TEST_FILE);

        File userFile = new File(UtilTest.USER_TEST_FILE);

        Assert.assertTrue(userFile.exists());

        leaderboardServiceTest = new LeaderboardService(UtilTest.GAME_TEST_FILE, UtilTest.USER_TEST_FILE);

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

    private void addFakeGamesToRegisteredUser() throws ServiceRequestFailedException {
        gameServiceTest.saveGame(new Game(gameServiceTest.generateMaze(10), 100, VALID_USER_ID, VALID_USER_EMAIL));
        gameServiceTest.saveGame(new Game(gameServiceTest.generateMaze(10), 90, VALID_USER_ID, VALID_USER_EMAIL));
        gameServiceTest.saveGame(new Game(gameServiceTest.generateMaze(10), 80, VALID_USER_ID, VALID_USER_EMAIL));
        gameServiceTest.saveGame(new Game(gameServiceTest.generateMaze(10), 100, VALID_USER_ID, VALID_USER_EMAIL));
    }
}
