package com.hit.service;

import com.hit.dm.Game;
import com.hit.dm.User;
import com.hit.util.ServiceRequestFailedException;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;

public class UserServiceTest implements IServiceTest{


    private static final String VALID_USER_PASSWORD = "USER_PASSWORD_TEST";
    private static final String VALID_USER_EMAIL = "USER_EMAIL_TEST";
    private static final String VALID_USER_USERNAME = "USER_USERNAME_TEST";
    private static final String INVALID_USER_PASSWORD = "INVALID_USER_PASSWORD_TEST";
    private static final String INVALID_USER_EMAIL = "INVALID_USER_EMAIL_TEST";
    private static String VALID_USER_ID = null;
    private static UserService userServiceTest = null;
    private static User newUser = new User(VALID_USER_EMAIL, VALID_USER_PASSWORD, VALID_USER_USERNAME);
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();


    @Override
    @Test
    public void checkEntityAdditionSuccess() throws ServiceRequestFailedException {
        String newUserEmail = "NEW_USER_EMAIL_TEST";

        String newUserPassword = "NEW_USER_PASSWORD_TEST";

        String newUserUsername = "NEW_USER_USERNAME_TEST";

        String userId = userServiceTest.register(newUserEmail, newUserPassword, newUserUsername);

        Assert.assertNotNull(userId);

        User user = userServiceTest.getUser(newUserEmail);

        Assert.assertEquals(userId, user.getUserId());
    }


    @Test(expected = ServiceRequestFailedException.class)
    public void checkEntityAdditionFailure() throws ServiceRequestFailedException {
        String existingUserEmail = VALID_USER_EMAIL;

        String existingUserName = VALID_USER_USERNAME;

        String existingUserPassword = VALID_USER_PASSWORD;

        userServiceTest.register(existingUserEmail, existingUserPassword, existingUserName);
    }



    @Override
    @Test
    public void checkEntityRetrievalSuccess() throws ServiceRequestFailedException {
        String userId = userServiceTest.login(VALID_USER_EMAIL, VALID_USER_PASSWORD);

        Assert.assertEquals(userId, VALID_USER_ID);
    }

    @Override
    @Test(expected = ServiceRequestFailedException.class)
    public void checkEntityRetrievalFailure() throws ServiceRequestFailedException {
        userServiceTest.login(INVALID_USER_EMAIL, INVALID_USER_PASSWORD);
    }

    @Override
    @Test(expected = ServiceRequestFailedException.class)
    public void checkEntityDeletionSuccess() throws ServiceRequestFailedException {
        userServiceTest.deleteUser(VALID_USER_EMAIL, VALID_USER_ID);

        userServiceTest.login(VALID_USER_EMAIL, VALID_USER_PASSWORD);
    }


    @Test(expected = ServiceRequestFailedException.class)
    public void checkEntityDeletionFailure() throws ServiceRequestFailedException {
        userServiceTest.deleteUser(INVALID_USER_EMAIL, VALID_USER_ID);
    }

    @Before
    public void setup() throws ServiceRequestFailedException {
        userServiceTest = new UserService(UtilTest.USER_TEST_FILE, UtilTest.GAME_TEST_FILE);

        File userFile = new File(UtilTest.USER_TEST_FILE);

        Assert.assertTrue(userFile.exists());

        VALID_USER_ID = userServiceTest.register(VALID_USER_EMAIL, VALID_USER_PASSWORD, VALID_USER_USERNAME);
    }

    @After
    public void teardown() {
        File userFile = new File(UtilTest.USER_TEST_FILE);
        userFile.delete();
        Assert.assertFalse(userFile.exists());

        File gameFile = new File(UtilTest.GAME_TEST_FILE);
        gameFile.delete();
        Assert.assertFalse(gameFile.exists());
    }
}
