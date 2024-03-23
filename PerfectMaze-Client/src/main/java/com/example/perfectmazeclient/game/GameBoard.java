package com.example.perfectmazeclient.game;

import com.example.perfectmazeclient.dm.PerfectMazeBoard;
import com.example.perfectmazeclient.dm.Point;

public class GameBoard {
    private PerfectMazeBoard mazeBoard;

    public GameBoard(PerfectMazeBoard perfectMazeBoard) {
        this.mazeBoard = perfectMazeBoard;
    }

    public boolean isValidMove(Point newPlayerLocation)
    {
        return newPlayerLocation.getX() >= 0 &&
                newPlayerLocation.getX() < mazeBoard.getColsWithWalls() &&
                newPlayerLocation.getY() >= 0 && newPlayerLocation.getY() < mazeBoard.getRowsWithWalls() &&
                mazeBoard.getMaze()[newPlayerLocation.getY()][newPlayerLocation.getX()] == 1;
    }

    public boolean isWinningMove(Point newPlayerLocation)
    {
        return mazeBoard.getEndingLocation().equals(newPlayerLocation);
    }

    public int getRowsWithWalls() {
        return mazeBoard.getRowsWithWalls();
    }

    public int getColsWithWalls() {
        return mazeBoard.getColsWithWalls();
    }

    public int[][] getMaze() {
        return mazeBoard.getMaze();
    }

    public Point getStartingLocation() {
        return mazeBoard.getStartingLocation();
    }

    public Point getEndingLocation() {
        return mazeBoard.getEndingLocation();
    }

}
