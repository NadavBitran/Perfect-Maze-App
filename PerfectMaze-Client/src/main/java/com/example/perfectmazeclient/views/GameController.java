package com.example.perfectmazeclient.views;

import com.example.perfectmazeclient.constants.WindowSize;
import com.example.perfectmazeclient.dm.PerfectMazeBoard;
import com.example.perfectmazeclient.dm.Point;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private final int RECTANGLE_DEFAULT_SIZE = 20;
    private final Color PLAYER_COLOR = Color.rgb(0, 123, 255);
    private int rectangleWidth = RECTANGLE_DEFAULT_SIZE;
    private int rectangleHeight = RECTANGLE_DEFAULT_SIZE;
    private PerfectMazeBoard perfectMazeBoard;
    private Point currentPlayerLocation;
    private Rectangle player;
    @FXML
    private GridPane mazeGridPane;
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    @FXML
    private Label time;
    int secondsElapsed  = 0;

    @FXML
    private Label youWon;
    @FXML
    private Label pressToStart;

    private Timeline timer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeFakeMazeData();
        setMaze();
        mazeGridPane.setFocusTraversable(true);
        mazeGridPane.setOnKeyPressed(this::handleKeyPress);
        youWon.setVisible(false);
    }
    private void setMaze() {
        int numRows = perfectMazeBoard.getRows();
        int numCols = perfectMazeBoard.getColumns();

        rectangleWidth =  WindowSize.WINDOW_WIDTH / numCols;
        rectangleHeight = WindowSize.WINDOW_HEIGHT / numRows;

        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++) {
                Rectangle cell = new Rectangle(rectangleWidth, rectangleHeight);
                cell.setFill(perfectMazeBoard.getMaze()[i][j] == 1 ? Color.WHITE : Color.BLACK);
                mazeGridPane.add(cell, j, i);
            }
        }

        currentPlayerLocation = new Point(perfectMazeBoard.getStartingLocation().getX(), perfectMazeBoard.getStartingLocation().getY());
        player = new Rectangle(rectangleWidth, rectangleHeight, PLAYER_COLOR);

        player.setWidth(rectangleWidth);
        player.setHeight(rectangleHeight);

        mazeGridPane.add(player, perfectMazeBoard.getStartingLocation().getX(), perfectMazeBoard.getStartingLocation().getY());
    }

    private void setTimer() {
        timer = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> updateTimer())
        );
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    private void updateTimer() {
        secondsElapsed++;
        int minutes = (secondsElapsed % 3600) / 60;
        int seconds = secondsElapsed % 60;

        String formattedTime = String.format("%02d:%02d", minutes, seconds);

        time.setText("Time: " + formattedTime);
    }

    private void stopTimer() {
        timer.stop();
    }


    private void handleKeyPress(KeyEvent event) {
        if (!gameStarted) {
            startGame();
            return;
        }

        if(gameEnded){
            toGoSummeryPage();
            return;
        }

        playerMoved(event.getCode());
    }

    private void startGame() {
        gameStarted = true;
        pressToStart.setVisible(false);
        setTimer();
    }
    public void playerMoved(KeyCode keycode)
    {
        int dx,dy;
        dx = dy = 0;
        switch (keycode) {
            case UP:
                dy = -1;
                break;
            case DOWN:
                dy = 1;
                break;
            case LEFT:
                dx = -1;
                break;
            case RIGHT:
                dx = 1;
                break;
        }

        if(isWinningMove(currentPlayerLocation.getX() + dx, currentPlayerLocation.getY() + dy)){
            youWon.setVisible(true);
            stopTimer();
            gameEnded = true;
        }

        if(isValidMove(currentPlayerLocation.getX() + dx, currentPlayerLocation.getY() + dy)){

            currentPlayerLocation.setX(currentPlayerLocation.getX() + dx);
            currentPlayerLocation.setY(currentPlayerLocation.getY() + dy);

            mazeGridPane.getChildren().remove(player);
            mazeGridPane.add(player, currentPlayerLocation.getX(), currentPlayerLocation.getY());
        }
    }

    public boolean isValidMove(int x, int y)
    {
        return x >= 0 && x < perfectMazeBoard.getColumns() && y >= 0 && y < perfectMazeBoard.getRows() && perfectMazeBoard.getMaze()[y][x] == 1;
    }

    public boolean isWinningMove(int x, int y)
    {
        return perfectMazeBoard.getEndingLocation().equals(new Point(x, y));
    }


    private void toGoSummeryPage() {
        try {
            Parent registerParent = FXMLLoader.load(getClass().getResource("game-results-summary.fxml"));
            Scene registerScene = new Scene(registerParent);

            Stage window = (Stage) mazeGridPane.getScene().getWindow();

            window.setScene(registerScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeFakeMazeData() {
        int[][] maze = {
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0},
                {0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0},
                {0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0},
                {0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0},
                {0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                {0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0},
                {0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0},
                {0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0},
                {0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0},
                {0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        String algorithm = "DFS";
        Point startingLocation = new Point(1, 0);
        Point endingLocation = new Point(1, 20);

        perfectMazeBoard = new PerfectMazeBoard();
        perfectMazeBoard.setMaze(maze);
        perfectMazeBoard.setStartingLocation(startingLocation);
        perfectMazeBoard.setEndingLocation(endingLocation);
        perfectMazeBoard.setAlgorithm(algorithm);
        perfectMazeBoard.setRows(maze.length);
        perfectMazeBoard.setColumns(maze[0].length);
        perfectMazeBoard.setRowsWithWalls(2*maze.length + 1);
        perfectMazeBoard.setColsWithWalls(2*maze[0].length + 1);
    }


}
