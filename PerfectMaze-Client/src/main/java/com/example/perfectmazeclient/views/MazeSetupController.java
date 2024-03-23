package com.example.perfectmazeclient.views;

import com.example.perfectmazeclient.constants.FXMLPaths;
import com.example.perfectmazeclient.validation.MazeSetupValidation;
import com.example.perfectmazeclient.util.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class MazeSetupController {
    @FXML
    private ChoiceBox<String> mazeSizeChoiceBox;
    @FXML
    private ChoiceBox<String> generatorAlgorithmChoiceBox;
    @FXML
    public void onBackButtonClicked(ActionEvent actionEvent) {
        PageLoader.loadPage(FXMLPaths.GAME_OPTIONS, actionEvent, getClass());
    }

    public void onStartButtonClicked(ActionEvent actionEvent) {
        String mazeSize = mazeSizeChoiceBox.getValue();
        String generatorAlgorithm = generatorAlgorithmChoiceBox.getValue();

        if (!MazeSetupValidation.validateMazeSetup(mazeSize, generatorAlgorithm)) return;

        PageLoader.loadPage(FXMLPaths.GAME_WINDOW, actionEvent, getClass());
    }

}
