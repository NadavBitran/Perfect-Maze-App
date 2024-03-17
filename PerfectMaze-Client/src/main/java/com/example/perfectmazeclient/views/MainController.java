package com.example.perfectmazeclient.views;

import com.example.perfectmazeclient.constants.FXMLPaths;
import com.example.perfectmazeclient.util.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {
    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        PageLoader.loadPage(FXMLPaths.LOGIN, event, getClass());
    }

    @FXML
    protected void onRegisterButtonClick(ActionEvent event) {
        PageLoader.loadPage(FXMLPaths.SIGN_UP, event, getClass());
    }

    @FXML
    protected void onExitButtonClick(ActionEvent event) {
        PageLoader.closePage(event);
    }
}
