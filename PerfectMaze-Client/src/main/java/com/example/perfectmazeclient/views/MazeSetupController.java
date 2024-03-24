package com.example.perfectmazeclient.views;

import com.example.perfectmazeclient.constants.FXMLPaths;
import com.example.perfectmazeclient.constants.MazeDifficulty;
import com.example.perfectmazeclient.dm.Game;
import com.example.perfectmazeclient.dm.PerfectMazeBoard;
import com.example.perfectmazeclient.containers.CurrentGameContainer;
import com.example.perfectmazeclient.validation.MazeSetupValidation;
import com.example.perfectmazeclient.util.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MazeSetupController implements Initializable {

    private MazeSetupValidation MazeSetupValidation;
    @FXML
    private ChoiceBox<String> mazeSizeChoiceBox;
    @FXML
    private ChoiceBox<String> generatorAlgorithmChoiceBox;
    @FXML
    private Label unselectedFieldsError;
    @FXML
    public void onBackButtonClicked(ActionEvent actionEvent) {
        PageLoader.loadPage(FXMLPaths.GAME_OPTIONS);
    }

    public void onStartButtonClicked(ActionEvent actionEvent) {

        clearUnselectedFieldsError();

        String mazeSize = mazeSizeChoiceBox.getValue();
        String generatorAlgorithm = generatorAlgorithmChoiceBox.getValue();

        if (!MazeSetupValidation.validateMazeSetup()) return;

        Game game = CurrentGameContainer.getCurrentGame();
        game.setMazeBoard(new PerfectMazeBoard());
        game.getMazeBoard().setAlgorithm(generatorAlgorithm);
        game.getMazeBoard().setRows(MazeDifficulty.DIFFICULTY_MAP.get(mazeSize));
        CurrentGameContainer.setCurrentGame(game);

        PageLoader.loadPage(FXMLPaths.GAME_WINDOW);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearUnselectedFieldsError();
        MazeSetupValidation = new MazeSetupValidation(mazeSizeChoiceBox, generatorAlgorithmChoiceBox, unselectedFieldsError);
    }

    private void clearUnselectedFieldsError() {
        unselectedFieldsError.setVisible(false);
        unselectedFieldsError.setManaged(false);
    }
}
