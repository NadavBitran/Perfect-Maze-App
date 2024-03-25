package com.hit.server;

import com.hit.constants.LocalRepositoryFileLocation;
import com.hit.controller.GameController;
import com.hit.controller.LeaderboardsController;
import com.hit.controller.UserController;
import com.hit.dao.Dao;
import com.hit.dm.GameList;
import com.hit.dm.User;
import com.hit.server.util.ControllersContainer;
import com.hit.server.util.ServicesContainer;
import com.hit.service.GameService;
import com.hit.service.LeaderboardService;
import com.hit.service.UserService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private int port;
    private ServerSocket serverSocket;
    private ExecutorService threadPool;
    private static final int THREAD_POOL_SIZE = 200;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try
        {
            initializeServer();
            initializeThreadPool();
            initializeServerComponents();

            while (true)
            {
                try
                {
                    Socket socket = serverSocket.accept();
                    threadPool.execute(new HandleRequest(socket));
                }
                catch (IOException e)
                {
                    System.err.println("Error accepting client connection: " + e.getMessage());
                }
            }
        }
        catch (IOException e)
        {
            System.err.println("Error initializing server: " + e.getMessage());
        }
        finally
        {
            stopServer();
        }
    }

    private void initializeServer() throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private void initializeThreadPool() {
        threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    private void initializeServerComponents() {
        Dao<User> userDao = new Dao<>(LocalRepositoryFileLocation.USER_FILE_LOCATION);
        Dao<GameList> gameDao = new Dao<>(LocalRepositoryFileLocation.GAME_FILE_LOCATION);

        ServicesContainer.setGameService(new GameService(userDao, gameDao));
        ServicesContainer.setUserService(new UserService(userDao, gameDao));
        ServicesContainer.setLeaderboardService(new LeaderboardService(userDao, gameDao));

        ControllersContainer.setUserController(new UserController(ServicesContainer.getUserService()));
        ControllersContainer.setGameController(new GameController(ServicesContainer.getGameService()));
        ControllersContainer.setLeaderboardController(new LeaderboardsController(ServicesContainer.getLeaderboardService()));
    }

    private void stopServer() {
        if (serverSocket == null || serverSocket.isClosed()) return;
        try
        {
            serverSocket.close();
            System.out.println("Server stopped.");
        }
        catch (IOException e)
        {
            System.err.println("Error closing server socket: " + e.getMessage());
        }
    }

}
