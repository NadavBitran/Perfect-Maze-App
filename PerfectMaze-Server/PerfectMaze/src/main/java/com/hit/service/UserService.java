package com.hit.service;

import com.hit.dao.Dao;
import com.hit.dm.GameList;
import com.hit.dm.User;


import com.hit.service.util.RollbackDaoUtil;
import com.hit.exceptions.ServiceRequestFailed;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;

public class UserService {
    private Dao<User> userDao;
    private Dao<GameList> gameListDao;
    private String userGameFile;

    public UserService(Dao<User> userDao, Dao<GameList> gameListDao) {
        this.userDao = userDao;
        this.gameListDao = gameListDao;
        this.userGameFile = userDao.getFilePath();
    }

    public String register(String email, String password, String username) throws ServiceRequestFailed {
        if(email == null) throw new ServiceRequestFailed("Failed to register: no email provided");
        if(password == null) throw new ServiceRequestFailed("Failed to register: no password provided");
        if(username == null) throw new ServiceRequestFailed("Failed to register: no username provided");

        User existingUser = userDao.find(email);

        if (existingUser != null)
            throw new ServiceRequestFailed("Failed to register: user with email " + email + " already exists");

        User newUser = new User(email, hashPassword(password), username);

        userDao.save(newUser.getEmail(), newUser);

        return newUser.getUserId();
    }

    public String login(String email, String password) throws ServiceRequestFailed {
        if(email == null) throw new ServiceRequestFailed("Failed to login: no email provided");
        if(password == null) throw new ServiceRequestFailed("Failed to login: no password provided");

        User existingUser = userDao.find(email);

        if (existingUser == null)
            throw new ServiceRequestFailed("Failed to login: invalid credentials");

        if (!verifyPassword(password, existingUser.getPassword()))
            throw new ServiceRequestFailed("Failed to login: invalid credentials");

        return existingUser.getUserId();
    }

    public User getUser(String email) throws ServiceRequestFailed {
        if(email == null) throw new ServiceRequestFailed("Failed to retrieve user: no email provided");

        User user = userDao.find(email);

        if(user == null)
            throw new ServiceRequestFailed("Failed to retrieve user with email " + email + ": user doesnt exist");

        return user;
    }

    public synchronized void deleteUser(String email, String userId) throws ServiceRequestFailed {
        if(email == null) throw new ServiceRequestFailed("Failed to delete user: no email provided");
        if(userId == null) throw new ServiceRequestFailed("Failed to delete user: no userId provided");

        try
        {
            RollbackDaoUtil.createBackupFile(userGameFile);
        }
        catch(IOException e)
        {
            throw new ServiceRequestFailed("Failed to delete user with email: " + email + ": failed to create backup file");
        }

        if(!userDao.delete(email))
        {
            RollbackDaoUtil.resetRollbackProperties();
            throw new ServiceRequestFailed("Failed to delete user with email: " + email + ": user doesn't exist");
        }

        RollbackDaoUtil.startTaskWithCaution(
                () -> gameListDao.delete(userId),
                "Failed to delete user with email: " + email + ": failed to delete user's games (CRITICAL ERROR)");
    }
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    private boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
