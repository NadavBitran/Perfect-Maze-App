package com.example.perfectmazeclient.views;

import com.example.perfectmazeclient.constants.FXMLPaths;
import com.example.perfectmazeclient.util.PageLoader;
import javafx.event.ActionEvent;

public class GameSummaryController {
    public void onContinueButtonClicked(ActionEvent actionEvent) {
        PageLoader.loadPage(FXMLPaths.GAME_OPTIONS, actionEvent, getClass());
    }

    public void onRetryButtonClicked(ActionEvent actionEvent) {
    }
}
