package com.example.perfectmazeclient.views;

import com.example.perfectmazeclient.constants.FXMLPaths;
import com.example.perfectmazeclient.util.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GameOptionsController {

    @FXML
    void onStartMazeButtonClicked(ActionEvent event) {
        PageLoader.loadPage(FXMLPaths.MAZE_SETUP, event, getClass());
    }

    @FXML
    void onViewLeaderboardButtonClicked(ActionEvent event) {
        PageLoader.loadPage(FXMLPaths.LEADERBOARD, event, getClass());
    }

    public void onSignOutButtonClicked(ActionEvent actionEvent) {
        PageLoader.loadPage(FXMLPaths.MAIN_MENU, actionEvent, getClass());
    }
}
