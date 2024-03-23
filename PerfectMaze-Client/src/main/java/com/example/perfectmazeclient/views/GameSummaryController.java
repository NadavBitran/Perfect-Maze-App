package com.example.perfectmazeclient.views;

import com.example.perfectmazeclient.constants.FXMLPaths;
import com.example.perfectmazeclient.constants.MazeDifficulty;
import com.example.perfectmazeclient.util.CurrentGame;
import com.example.perfectmazeclient.util.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class GameSummaryController implements Initializable {

    @FXML
    private Label timeToCompleteLabel;

    @FXML
    private Label algorithmChosenLabel;

    @FXML
    private Label mazeDifficultyLabel;

    public void onContinueButtonClicked(ActionEvent actionEvent) {
        PageLoader.loadPage(FXMLPaths.GAME_OPTIONS, actionEvent, getClass());
    }

    public void onRetryButtonClicked(ActionEvent actionEvent) {
        CurrentGame.setIsTryingToImprove(true);
        PageLoader.loadPage(FXMLPaths.GAME_WINDOW, actionEvent, getClass());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeToCompleteLabel.setText("Time to complete: " + CurrentGame.getCurrentGame().getTimeToSolve());
        algorithmChosenLabel.setText("Algorithm chosen: " + CurrentGame.getCurrentGame().getMazeBoard().getAlgorithm());
        mazeDifficultyLabel.setText("Maze difficulty: " + MazeDifficulty.REVERSE_DIFFICULTY_MAP.get(CurrentGame.getCurrentGame().getMazeBoard().getRows()));
    }
}
