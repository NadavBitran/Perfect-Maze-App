package com.hit.dm;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a game entity with attributes such as maze board, starting and ending locations, time to solve, etc.
 */
public class Game implements Serializable {
    private String gameId;
    private PerfectMazeBoard mazeBoard;
    private int timeToSolve;
    private String userId;
    private String userEmail;

    /**
     * Constructs a new Game instance with the provided parameters.
     *
     * @param mazeBoard        The maze board represented as a 2D array of integers, board size and start and end locations.
     * @param timeToSolve      The time taken to solve the maze.
     * @param userId           The unique identifier of the user associated with the game.
     */
    public Game(PerfectMazeBoard mazeBoard, int timeToSolve, String userId, String userEmail) {
        this.gameId = UUID.randomUUID().toString();
        this.mazeBoard = mazeBoard;
        this.timeToSolve = timeToSolve;
        this.userId = userId;
        this.userEmail = userEmail;
    }

    public Game() {
        this.gameId = UUID.randomUUID().toString();
    }

    // Getters and setters for class attributes

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public PerfectMazeBoard getMazeBoard() {
        return mazeBoard;
    }

    public void setMazeBoard(PerfectMazeBoard mazeBoard) {
        this.mazeBoard = mazeBoard;
    }

    public int getTimeToSolve() {
        return timeToSolve;
    }

    public void setTimeToSolve(int timeToSolve) {
        this.timeToSolve = timeToSolve;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    // Equals, hashCode, and toString methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return this.gameId.equals(game.gameId);
    }


    @Override
    public int hashCode() {
        return Objects.hash(this.gameId);
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId='" + gameId + '\'' +
                ", mazeBoard=" + mazeBoard.toString() +
                ", timeToSolve=" + timeToSolve +
                ", userId='" + userId + '\'' +
                '}';
    }
}
