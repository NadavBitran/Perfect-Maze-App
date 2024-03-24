package com.hit.service;

import com.hit.constants.LocalRepositoryFileLocation;
import com.hit.dao.Dao;
import com.hit.dm.GameList;
import com.hit.dm.User;
import com.hit.exceptions.ServiceRequestFailed;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;


import static com.hit.service.UtilTest.*;

public class UserServiceTest implements IServiceTest{
    private String validUserId = null;
    private static UserService userServiceTest;
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void setupClass() {
        userServiceTest = new UserService(
                new Dao<>(LocalRepositoryFileLocation.USER_FILE_LOCATION),
                new Dao<>(LocalRepositoryFileLocation.GAME_FILE_LOCATION));
    }

    @Before
    public void setup() throws ServiceRequestFailed {
        validUserId = userServiceTest.register(VALID_USER_EMAIL, VALID_USER_PASSWORD, VALID_USER_USERNAME);
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
        String newUserEmail = "NEW_USER_EMAIL_TEST";

        String newUserPassword = "NEW_USER_PASSWORD_TEST";

        String newUserUsername = "NEW_USER_USERNAME_TEST";

        String userId = userServiceTest.register(newUserEmail, newUserPassword, newUserUsername);

        Assert.assertNotNull(userId);

        User user = userServiceTest.getUser(newUserEmail);

        Assert.assertEquals(userId, user.getUserId());
    }


    @Test(expected = ServiceRequestFailed.class)
    public void checkEntityAdditionFailure() throws ServiceRequestFailed {
        String existingUserEmail = VALID_USER_EMAIL;

        String existingUserName = VALID_USER_USERNAME;

        String existingUserPassword = VALID_USER_PASSWORD;

        userServiceTest.register(existingUserEmail, existingUserPassword, existingUserName);
    }



    @Override
    @Test
    public void checkEntityRetrievalSuccess() throws ServiceRequestFailed {
        String userId = userServiceTest.login(VALID_USER_EMAIL, VALID_USER_PASSWORD);

        Assert.assertEquals(userId, validUserId);
    }

    @Override
    @Test(expected = ServiceRequestFailed.class)
    public void checkEntityRetrievalFailure() throws ServiceRequestFailed {
        userServiceTest.login(INVALID_USER_EMAIL, INVALID_USER_PASSWORD);
    }

    @Override
    @Test(expected = ServiceRequestFailed.class)
    public void checkEntityDeletionSuccess() throws ServiceRequestFailed {
        userServiceTest.deleteUser(VALID_USER_EMAIL, validUserId);

        userServiceTest.login(VALID_USER_EMAIL, VALID_USER_PASSWORD);
    }


    @Test(expected = ServiceRequestFailed.class)
    public void checkEntityDeletionFailure() throws ServiceRequestFailed {
        userServiceTest.deleteUser(INVALID_USER_EMAIL, validUserId);
    }

}
