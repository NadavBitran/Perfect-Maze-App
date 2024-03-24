package com.example.perfectmazeclient.views;

import com.example.perfectmazeclient.constants.FXMLPaths;
import com.example.perfectmazeclient.constants.WindowSize;
import com.example.perfectmazeclient.dm.Point;
import com.example.perfectmazeclient.exceptions.RequestFailed;
import com.example.perfectmazeclient.game.GameBoard;
import com.example.perfectmazeclient.game.GameStatus;
import com.example.perfectmazeclient.game.GameTimer;
import com.example.perfectmazeclient.game.Player;
import com.example.perfectmazeclient.requests.handlers.GameRequests;
import com.example.perfectmazeclient.util.AlertError;
import com.example.perfectmazeclient.containers.CurrentGameContainer;
import com.example.perfectmazeclient.util.PageLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private int rectangleWidth;
    private int rectangleHeight;
    private Player player;
    private GameBoard gameBoard;
    private GameTimer timer;
    private GameStatus gameStatus;
    @FXML
    private GridPane mazeGridPane;
    @FXML
    private Label time;
    @FXML
    private Label youWon;
    @FXML
    private Label pressToStart;
    @FXML
    private Label errorLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameStatus = GameStatus.NOT_STARTED;

        try
        {
            if (!CurrentGameContainer.isTryingToImprove()) initializeMazeDataFromServer();

            gameBoard = new GameBoard(CurrentGameContainer.getCurrentGame().getMazeBoard());

            initializeGameWindow();
            initializeWindowEntities();
        }
        catch (RequestFailed e)
        {
            gameStatus = GameStatus.ERROR;
            initializeWindowEntities();
            initializeGameLoadError();
        }
    }

    private void initializeGameWindow() {
        int numRows = gameBoard.getRowsWithWalls();
        int numCols = gameBoard.getColsWithWalls();

        rectangleWidth =  WindowSize.WINDOW_WIDTH / numCols;
        rectangleHeight = WindowSize.WINDOW_HEIGHT / numRows;

        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++) {
                Rectangle cell = new Rectangle(rectangleWidth, rectangleHeight);
                cell.setFill(gameBoard.getMaze()[i][j] == 1 ? Color.WHITE : Color.BLACK);
                mazeGridPane.add(cell, j, i);
            }
        }

        player = new Player(
                rectangleWidth,
                rectangleHeight,
                gameBoard.getStartingLocation());

        mazeGridPane.add(
                player.getPlayerShape(),
                gameBoard.getStartingLocation().getX(),
                gameBoard.getStartingLocation().getY());

        timer = new GameTimer(time);

    }


    private void initializeWindowEntities()
    {
        mazeGridPane.setFocusTraversable(true);
        mazeGridPane.setOnKeyPressed(this::handleKeyPress);
        youWon.setVisible(false);
        errorLabel.setVisible(false);
    }

    private void initializeGameLoadError()
    {
        youWon.setVisible(false);
        pressToStart.setVisible(false);
        errorLabel.setVisible(true);
    }



    private void handleKeyPress(KeyEvent event) {

        switch(gameStatus)
        {
            case NOT_STARTED:
                startGame();
                break;
            case IN_PROGRESS:
                playerMoved(event.getCode());
                break;
            case FINISHED:
                concludeGame();
                break;
            case ERROR:
                concludeError();
                break;
        }
    }

    private void startGame() {
        pressToStart.setVisible(false);
        timer.setTimer();
        gameStatus = GameStatus.IN_PROGRESS;
    }
    private void endGame() {
        youWon.setVisible(true);
        timer.stopTimer();
        gameStatus = GameStatus.FINISHED;
    }
    private void updateMove(Point newPlayerLocation) {
        player.setCurrentPlayerLocation(newPlayerLocation);
        mazeGridPane.getChildren().remove(player.getPlayerShape());
        mazeGridPane.add(player.getPlayerShape(), newPlayerLocation.getX(), newPlayerLocation.getY());
    }

    public void playerMoved(KeyCode keycode)
    {
        int dx,dy;

        dx = dy = 0;

        switch (keycode)
        {
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

        Point newPlayerLocation = new Point(player.getCurrentPlayerLocation().getX() + dx, player.getCurrentPlayerLocation().getY() + dy);

        if(gameBoard.isWinningMove(newPlayerLocation)) endGame();

        if(gameBoard.isValidMove(newPlayerLocation)) updateMove(newPlayerLocation);
    }


    private void initializeMazeDataFromServer() throws RequestFailed {
        GameRequests.handleMazeGenerationRequest(
                    CurrentGameContainer.getCurrentGame().getMazeBoard().getRows(),
                    CurrentGameContainer.getCurrentGame().getMazeBoard().getAlgorithm());

    }

    private void concludeGame() {
        try
        {
            if (CurrentGameContainer.isTryingToImprove())
            {
                int timeImprovement = CurrentGameContainer.getCurrentGame().getTimeToSolve() - timer.getSecondsElapsed();
                CurrentGameContainer.setTimeImprovement(timeImprovement);

                if (timeImprovement > 0) GameRequests.handleMazeImprovementRequest(CurrentGameContainer.getCurrentGame().getGameId(), timer.getSecondsElapsed());
            }
            else
            {
                GameRequests.handleMazeSolvedRequest(CurrentGameContainer.getCurrentGame().getMazeBoard(), timer.getSecondsElapsed());
            }

            PageLoader.loadPage(FXMLPaths.GAME_RESULTS_SUMMARY);
        }
        catch (RequestFailed e)
        {
            PageLoader.loadPage(FXMLPaths.GAME_OPTIONS);
            AlertError.showAlertError("Error", "Game Results", e.getMessage());
        }
    }

    private void concludeError() {
        CurrentGameContainer.setIsTryingToImprove(false);
        PageLoader.loadPage(FXMLPaths.GAME_OPTIONS);
    }

}
