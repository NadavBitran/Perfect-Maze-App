package com.hit.dm;

import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Represents a perfect maze board with starting and ending locations.
 */
public class PerfectMazeBoard implements Serializable {
    private int[][] maze;
    private Point startingLocation;
    private Point endingLocation;
    private int rows;
    private int columns;
    private int rowsWithWalls;
    private int colsWithWalls;
    private String algorithm;

    /**
     * Constructs a PerfectMazeBoard with provided maze and size.
     *
     * @param maze     The maze represented as a 2D array of integers.
     * @param mazeSize The size of the maze.
     */
    public PerfectMazeBoard(int[][] maze, int mazeSize, Point endingLocation) {
        this.maze = maze;
        this.startingLocation = new Point(0, 1);
        this.endingLocation = endingLocation;
        this.rows = mazeSize;
        this.columns = mazeSize;
        this.rowsWithWalls = mazeSize*2 + 1;
        this.colsWithWalls = mazeSize*2 + 1;
    }

    /**
     * Constructs an empty PerfectMazeBoard.
     */
    public PerfectMazeBoard() {
    }

    public int getRowsWithWalls() {
        return rowsWithWalls;
    }

    public void setRowsWithWalls(int rowsWithWalls) {
        this.rowsWithWalls = rowsWithWalls;
    }

    public int getColsWithWalls() {
        return colsWithWalls;
    }

    public void setColsWithWalls(int colsWithWalls) {
        this.colsWithWalls = colsWithWalls;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int[][] getMaze() {
        return maze;
    }

    public void setMaze(int[][] maze) {
        this.maze = maze;
    }

    public Point getStartingLocation() {
        return startingLocation;
    }

    public void setStartingLocation(Point startingLocation) {
        this.startingLocation = startingLocation;
    }

    public Point getEndingLocation() {
        return endingLocation;
    }

    public void setEndingLocation(Point endingLocation) {
        this.endingLocation = endingLocation;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String toString() {
        return "PerfectMazeBoard{" +
                "maze=" + Arrays.deepToString(maze) +
                ", startingLocation=" + startingLocation +
                ", endingLocation=" + endingLocation +
                ", rows=" + rows +
                ", columns=" + columns +
                '}';
    }
}
