package com.example.perfectmazeclient.views;

import com.example.perfectmazeclient.constants.FXMLPaths;
import com.example.perfectmazeclient.containers.CurrentLoggedUserContainer;
import com.example.perfectmazeclient.util.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GameOptionsController {

    @FXML
    void onStartMazeButtonClicked(ActionEvent event) {
        PageLoader.loadPage(FXMLPaths.MAZE_SETUP);
    }

    @FXML
    void onViewLeaderboardButtonClicked(ActionEvent event) {
        PageLoader.loadPage(FXMLPaths.LEADERBOARD);
    }

    public void onSignOutButtonClicked(ActionEvent actionEvent) {
        CurrentLoggedUserContainer.setLoggedUser(null);
        PageLoader.loadPage(FXMLPaths.MAIN_MENU);
    }
}
