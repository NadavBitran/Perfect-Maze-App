package com.example.perfectmazeclient.views;

import com.example.perfectmazeclient.constants.FXMLPaths;
import com.example.perfectmazeclient.constants.MazeDifficulty;
import com.example.perfectmazeclient.containers.CurrentGameContainer;
import com.example.perfectmazeclient.util.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class GameSummaryController implements Initializable {

    private static final Color POSITIVE_IMPROVEMENT_COLOR = Color.rgb(46, 121, 62);
    private static final Color NEGATIVE_IMPROVEMENT_COLOR = Color.rgb(121, 46, 46);
    @FXML
    private Label timeToCompleteLabel;

    @FXML
    private Label algorithmChosenLabel;

    @FXML
    private Label mazeDifficultyLabel;

    @FXML
    private Label timeImprovementLabelTitle;
    @FXML
    private Label timeImprovementLabelDetail;

    public void onContinueButtonClicked(ActionEvent actionEvent) {
        CurrentGameContainer.setCurrentGame(null);
        CurrentGameContainer.setIsTryingToImprove(false);
        PageLoader.loadPage(FXMLPaths.GAME_OPTIONS, actionEvent, getClass());
    }

    public void onRetryButtonClicked(ActionEvent actionEvent) {
        CurrentGameContainer.setIsTryingToImprove(true);
        PageLoader.loadPage(FXMLPaths.GAME_WINDOW, actionEvent, getClass());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLabels();

        if(CurrentGameContainer.isTryingToImprove()) setTimeImprovementLabel();
    }

    private void setLabels() {
        timeImprovementLabelDetail.setVisible(false);
        timeImprovementLabelTitle.setVisible(false);
        timeToCompleteLabel.setText(CurrentGameContainer.getCurrentGame().getTimeToSolve() + " Seconds");
        algorithmChosenLabel.setText(CurrentGameContainer.getCurrentGame().getMazeBoard().getAlgorithm());
        mazeDifficultyLabel.setText(MazeDifficulty.REVERSE_DIFFICULTY_MAP.get(CurrentGameContainer.getCurrentGame().getMazeBoard().getRows()));
    }
    private void setTimeImprovementLabel() {
        timeImprovementLabelDetail.setVisible(true);
        timeImprovementLabelTitle.setVisible(true);

        if(CurrentGameContainer.getTimeImprovement() > 0)
        {
            timeImprovementLabelDetail.setText("-" + CurrentGameContainer.getTimeImprovement() + " Seconds");
            timeImprovementLabelDetail.setTextFill(POSITIVE_IMPROVEMENT_COLOR);
        } else
        {
            timeImprovementLabelDetail.setText("+" + Math.abs(CurrentGameContainer.getTimeImprovement()) + " Seconds");
            timeImprovementLabelDetail.setTextFill(NEGATIVE_IMPROVEMENT_COLOR);
        }
    }

}
