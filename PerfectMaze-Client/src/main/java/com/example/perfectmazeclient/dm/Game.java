package com.example.perfectmazeclient.dm;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Game  {
    private String gameId;
    private PerfectMazeBoard mazeBoard;
    private int timeToSolve;
    private String userId;
    private String userEmail;

    public Game(PerfectMazeBoard mazeBoard, int timeToSolve) {
        this.mazeBoard = mazeBoard;
        this.timeToSolve = timeToSolve;
    }

    public Game(PerfectMazeBoard mazeBoard)
    {
        this.mazeBoard = mazeBoard;
    }

    public Game() {}

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
